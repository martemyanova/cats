package com.techtask.cats.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtask.cats.common.Result
import com.techtask.cats.common.util.SingleLiveEvent
import com.techtask.cats.domain.model.Cat
import com.techtask.cats.domain.usecase.FetchFirstBreedsUseCase
import com.techtask.cats.domain.usecase.SearchBreedsUseCase
import com.techtask.cats.domain.usecase.FetchCatsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatsListViewModel @Inject constructor(
    private var fetchCatsUseCase: FetchCatsUseCase,
    private val searchBreedsUseCase: SearchBreedsUseCase,
    private var fetchFirstBreedsUseCase: FetchFirstBreedsUseCase
) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    val loadingState = SingleLiveEvent<CatsListViewState>()

    private var breedId: String? = null

    fun loadData() {
        viewModelScope.launch {
            if (breedId == null) {
                fetchFirstBreedsUseCase.execute().apply {
                    if (this is Result.Success) {
                        breedId = this.data.id
                    } else {
                        loadingState.value = CatsListViewState.ERROR
                    }
                }
            }
            breedId?.let { loadData(it) }
        }
    }

    private fun loadData(breedId: String) {
        loadingState.value = CatsListViewState.LOADING
        viewModelScope.launch {
            fetchCatsUseCase.execute(breedId).handleResult { result ->
                _cats.value = result.data
                loadingState.value = CatsListViewState.DATA_READY
            }
        }
    }

    fun onSearchRequest(query: String) {
        loadingState.value = CatsListViewState.LOADING
        viewModelScope.launch {
            searchBreedsUseCase.execute(query).handleResult { result ->
                breedId = result.data.get(0).id
                breedId?.let { loadData(it) }
            }
        }
    }

    private fun <D> Result<List<D>>.handleResult(
        onSuccessResult: (result: Result.Success<List<D>>) -> Unit
    ) {
        if (this is Result.Success) {
            if (this.data.isNotEmpty()) {
                onSuccessResult(this)
            } else {
                loadingState.value = CatsListViewState.NOTHING_FOUND
            }
        } else {
            loadingState.value = CatsListViewState.ERROR
        }
    }
}

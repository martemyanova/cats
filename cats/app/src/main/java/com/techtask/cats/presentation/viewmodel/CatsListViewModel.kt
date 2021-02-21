package com.techtask.cats.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtask.cats.common.Result
import com.techtask.cats.common.util.SingleLiveEvent
import com.techtask.cats.domain.model.Breed
import com.techtask.cats.domain.model.Cat
import com.techtask.cats.domain.usecase.FetchAllBreedsUseCase
import com.techtask.cats.domain.usecase.SearchBreedsUseCase
import com.techtask.cats.domain.usecase.FetchCatsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatsListViewModel @Inject constructor(
    private var fetchCatsUseCase: FetchCatsUseCase,
    private val searchBreedsUseCase: SearchBreedsUseCase,
    private var fetchAllBreedsUseCase: FetchAllBreedsUseCase
) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    val loadingState = SingleLiveEvent<LoadingState>()

    private var breeds: List<Breed>? = null

    fun loadData() {
        viewModelScope.launch {
            val result = fetchAllBreedsUseCase.execute()
            if (result is Result.Success) {
                Log.d("fetchCats", "fetchAllBreedsUseCase, breeds count: ${result.data.size}")
                breeds = result.data
                if (result.data.isNotEmpty()) {
                    val firsBreedResult = result.data.get(0)
                    loadData(firsBreedResult.id)
                }
            }
        }
    }

    private fun loadData(breedId: String) {
        Log.d("fetchCats", "loadData, breedId: $breedId")
        loadingState.value = LoadingState.LOADING
        viewModelScope.launch {
            val result = fetchCatsUseCase.execute(breedId)
            if (result is Result.Success) {
                _cats.value = result.data
            } else {
                loadingState.value = LoadingState.ERROR
            }
        }
    }

    fun onSearchRequest(query: String) {
        Log.d("fetchCats", "onSearchRequest, query: $query")
        viewModelScope.launch {
            val result = searchBreedsUseCase.execute(query)
            if (result is Result.Success) {
                Log.d("fetchCats", "breeds count: ${result.data.size}")
                if (result.data.isNotEmpty()) {
                    val breedId = result.data.get(0).id
                    loadData(breedId)
                }
            }
        }
    }

    enum class LoadingState { LOADING, ERROR }
}

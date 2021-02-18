package com.techtask.cats.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtask.cats.domain.model.Cat
import com.techtask.cats.domain.usecase.FetchCatsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatsListViewModel @Inject constructor(
    private var fetchCatsUseCase: FetchCatsUseCase
) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    fun loadData() {
        viewModelScope.launch {
            val result = fetchCatsUseCase.execute()
            if (result is FetchCatsUseCase.Result.Success) {
                _cats.value = result.cats
            }
        }
    }
}

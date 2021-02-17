package com.techtask.cats.domain.usecase

import android.util.Log
import com.techtask.cats.data.CatsApi
import com.techtask.cats.domain.model.Cat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchCatsUseCase @Inject constructor(
    private val catsApi: CatsApi
) {
    sealed class Result {
        data class Success(val cats: List<Cat>) : Result()
        object Failure: Result()
    }

    suspend fun execute(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.getCats(20)
                if (response.isSuccessful && response.body() != null) {

                    Log.d("fetchCats", "${response.body()}")
                    val catResponse = response.body()!!
                    return@withContext Result.Success(catResponse.map {
                        Cat(name = it.id)
                    })
                } else {
                    Log.d("fetchCats", "Failure")
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.d("fetchCats", "Failure", t)
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }
}

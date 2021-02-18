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
                val response = catsApi.getCats(RESPONSE_LIMIT)
                if (response.isSuccessful && response.body() != null) {

                    Log.d(TAG, "${response.body()}")
                    val catResponse = response.body()!!
                    return@withContext Result.Success(catResponse.map {
                        val breed = it.breeds?.getOrNull(0)
                        Cat(
                            imageUrl = it.url,
                            breed = breed?.name,
                            temperament = breed?.temperament)
                    })
                } else {
                    Log.d("TAG", "Failure")
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.e(TAG, "Failure", t)
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }

    companion object {
        private const val TAG = "FetchCatsUseCase"
        private const val RESPONSE_LIMIT = 50
    }
}

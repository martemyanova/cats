package com.techtask.cats.data

import android.util.Log
import com.techtask.cats.domain.model.Breed
import com.techtask.cats.domain.model.Cat
import com.techtask.cats.common.Result
import com.techtask.cats.data.CatsApi.Companion.PARAM_IMAGE_SIZE_MEDIUM
import com.techtask.cats.data.schema.BreedSchema
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val catsApi: CatsApi
) {
    suspend fun retrieveBreeds(): Result<List<Breed>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.getBreeds()
                if (response.isSuccessful && response.body() != null) {

                    Log.d(TAG, "${response.body()}")
                    val breedResponse = response.body()!!
                    return@withContext Result.Success(breedResponse.map {
                        it.parse()
                    })
                } else {
                    Log.d(TAG, "Failure")
                    return@withContext Result.Failure()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.e(TAG, "Failure", t)
                    return@withContext Result.Failure(t)
                } else {
                    throw t
                }
            }
        }
    }

    suspend fun retrieveCats(breedId: String): Result<List<Cat>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.getCats(PARAM_IMAGE_SIZE_MEDIUM, RESPONSE_LIMIT, breedId)
                if (response.isSuccessful && response.body() != null) {

                    Log.d(TAG, "${response.body()}")
                    val catResponse = response.body()!!
                    return@withContext Result.Success(catResponse.map {
                        it.parse()
                    })
                } else {
                    Log.d(TAG, "Failure")
                    return@withContext Result.Failure()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.e(TAG, "Failure", t)
                    return@withContext Result.Failure(t)
                } else {
                    throw t
                }
            }
        }
    }

    private fun CatSchema.parse(): Cat =
        Cat(
            id = this.id,
            breeds = this.parseBreeds(),
            imageUrl = this.imageUrl)

    private fun CatSchema.parseBreeds(): List<Breed> =
        breeds?.map {
            it.parse()
        } ?: emptyList()

    private fun BreedSchema.parse(): Breed =
        Breed(
            id = this.id,
            name = this.name,
            temperament = this.temperament,
            origin = this.origin,
            description = this.description,
            wikipediaUrl = this.wikipediaUrl)

    companion object {
        private const val TAG = "RemoteDataSource"
        private const val RESPONSE_LIMIT = 50
    }
}

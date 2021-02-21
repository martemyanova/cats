package com.techtask.cats.data

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
    suspend fun searchBreedsByName(searchString: String): Result<List<Breed>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.searchBreedsByName(searchString)
                if (response.isSuccessful && response.body() != null) {

                    val breedResponse = response.body()!!
                    return@withContext Result.Success(breedResponse.map {
                        it.parse()
                    })
                } else {
                    return@withContext Result.Failure()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure(t)
                } else {
                    throw t
                }
            }
        }
    }

    suspend fun retrieveFirstBreed(): Result<Breed> {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.getBreeds(BREEDS_RESPONSE_LIMIT)
                if (response.isSuccessful && response.body() != null) {

                    val breedResponse = response.body()!!
                    return@withContext Result.Success(breedResponse
                        .map { it.parse() }
                        .get(0))
                } else {
                    return@withContext Result.Failure()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
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
                val response = catsApi.getCats(PARAM_IMAGE_SIZE_MEDIUM, CATS_RESPONSE_LIMIT, breedId)
                if (response.isSuccessful && response.body() != null) {

                    val catResponse = response.body()!!
                    return@withContext Result.Success(catResponse.map {
                        it.parse()
                    })
                } else {
                    return@withContext Result.Failure()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
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
        private const val CATS_RESPONSE_LIMIT = 50
        private const val BREEDS_RESPONSE_LIMIT = 1
    }
}

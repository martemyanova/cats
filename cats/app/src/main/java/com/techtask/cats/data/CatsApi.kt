package com.techtask.cats.data

import com.techtask.cats.data.schema.BreedSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsApi {

    @Headers(CATS_API_KEY_HEADER)
    @GET(BREED_SEARCH)
    suspend fun getBreeds(): Response<List<BreedSchema>>

    @Headers(CATS_API_KEY_HEADER)
    @GET(IMAGE_SEARCH)
    suspend fun getCats(
        @Query("size") size: String,
        @Query("limit") limit: Int,
        @Query("breed_id") breedId: String
    ): Response<List<CatSchema>>

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
        private const val BREED_SEARCH = "breeds"
        private const val IMAGE_SEARCH = "images/search"
        private const val CATS_API_KEY_HEADER = "x-api-key: 52b2f999-f951-4c22-9096-9c4befed4caa"

        const val PARAM_IMAGE_SIZE_MEDIUM = "med"
    }
}

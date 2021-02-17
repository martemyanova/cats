package com.techtask.cats.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsApi {

    @Headers(CATS_API_KEY_HEADER)
    @GET(IMAGES_SEARCH)
    suspend fun getCats(@Query("limit") limit: Int): Response<List<CatResponse>>

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/"
        const val IMAGES_SEARCH = "v1/images/search"
        const val CATS_API_KEY_HEADER = "x-api-key: 52b2f999-f951-4c22-9096-9c4befed4caa"
    }
}

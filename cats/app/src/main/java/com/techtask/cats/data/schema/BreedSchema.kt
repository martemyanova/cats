package com.techtask.cats.data.schema

import com.google.gson.annotations.SerializedName

data class BreedSchema(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("description") val description: String,
    @SerializedName("wikipedia_url") val wikipediaUrl: String
)

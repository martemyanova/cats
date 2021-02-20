package com.techtask.cats.data

import com.google.gson.annotations.SerializedName
import com.techtask.cats.data.schema.BreedSchema

data class CatSchema(
    @SerializedName("id") val id: String,
    @SerializedName("url") val imageUrl: String,
    @SerializedName("breeds") val breeds: List<BreedSchema>?
)

package com.techtask.cats.domain.model

data class Breed(
    val id: String,
    val name: String,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    val wikipediaUrl: String?
)

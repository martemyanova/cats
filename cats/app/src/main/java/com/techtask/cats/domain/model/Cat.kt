package com.techtask.cats.domain.model

data class Cat(
    val id: String,
    val breeds: List<Breed>,
    val imageUrl: String,
)

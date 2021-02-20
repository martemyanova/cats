package com.techtask.cats.common

sealed class Result<out D> {

    data class Success<out D>(val data: D) : Result<D>()

    data class Failure(val exception: Throwable? = null) : Result<Nothing>()
}

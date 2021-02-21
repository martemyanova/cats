package com.techtask.cats.domain.usecase

import com.techtask.cats.common.Result
import com.techtask.cats.data.RemoteDataSource
import com.techtask.cats.domain.model.Breed
import javax.inject.Inject

class FetchFirstBreedsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun execute(): Result<Breed> {
        return remoteDataSource.retrieveFirstBreed()
    }
}

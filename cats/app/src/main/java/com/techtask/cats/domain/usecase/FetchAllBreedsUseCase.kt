package com.techtask.cats.domain.usecase

import com.techtask.cats.common.Result
import com.techtask.cats.data.RemoteDataSource
import com.techtask.cats.domain.model.Breed
import javax.inject.Inject

class FetchAllBreedsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun execute(): Result<List<Breed>> {
        return remoteDataSource.retrieveBreeds()
    }
}

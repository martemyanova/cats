package com.techtask.cats.domain.usecase

import com.techtask.cats.common.Result
import com.techtask.cats.data.RemoteDataSource
import com.techtask.cats.domain.model.Cat
import javax.inject.Inject

class FetchCatsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun execute(breedId: String): Result<List<Cat>> {
        return remoteDataSource.retrieveCats(breedId)
    }
}

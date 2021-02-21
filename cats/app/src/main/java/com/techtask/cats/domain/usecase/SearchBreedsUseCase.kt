package com.techtask.cats.domain.usecase

import com.techtask.cats.common.Result
import com.techtask.cats.data.RemoteDataSource
import com.techtask.cats.domain.model.Breed
import javax.inject.Inject

class SearchBreedsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun execute(searchString: String): Result<List<Breed>> {
        return remoteDataSource.searchBreedsByName(searchString)
    }
}

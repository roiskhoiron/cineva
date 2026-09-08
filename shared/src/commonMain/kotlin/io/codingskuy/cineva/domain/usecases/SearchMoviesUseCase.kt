package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.entities.PaginatedMovies
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(query: String, page: Int = 1): Flow<Result<PaginatedMovies>> {
        if (query.isBlank()) {
            return flow { emit(Result.Success(PaginatedMovies.empty(page))) }
        }
        return repository.searchMovies(query.trim(), page)
    }
}

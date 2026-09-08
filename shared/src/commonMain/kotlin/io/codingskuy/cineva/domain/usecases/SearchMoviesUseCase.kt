package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import io.codingskuy.cineva.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(query: String): Flow<Result<List<Movie>>> {
        if (query.isBlank()) {
            return flow { emit(Result.Success(emptyList())) }
        }
        return repository.searchMovies(query.trim())
    }
}

package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase(private val repository: MovieRepository) {
    operator fun invoke(imdbID: String): Flow<Boolean> = repository.isFavorite(imdbID)
}

package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

class ToggleFavoriteUseCase(private val repository: MovieRepository) {
    operator fun invoke(movie: Movie): Flow<Result<Unit>> = repository.toggleFavorite(movie)
}

package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.getFavorites()
}

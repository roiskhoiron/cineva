package io.codingskuy.cineva.domain.repositories

import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.entities.MovieDetail
import io.codingskuy.cineva.domain.entities.PaginatedMovies
import kotlinx.coroutines.flow.Flow

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: String, val throwable: Throwable? = null) : Result<Nothing>
    data object Loading : Result<Nothing>
}

interface MovieRepository {
    fun searchMovies(query: String, page: Int = 1): Flow<Result<PaginatedMovies>>
    fun getMovieDetail(imdbID: String): Flow<Result<MovieDetail>>
    fun getFavorites(): Flow<List<Movie>>
    fun toggleFavorite(movie: Movie): Flow<Result<Unit>>
    fun isFavorite(imdbID: String): Flow<Boolean>
}

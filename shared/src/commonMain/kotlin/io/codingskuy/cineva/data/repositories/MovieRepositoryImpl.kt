package io.codingskuy.cineva.data.repositories

import io.codingskuy.cineva.data.datasources.local.LocalDataSource
import io.codingskuy.cineva.data.datasources.remote.OMDbRemoteDataSource
import io.codingskuy.cineva.data.models.toEntity
import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.entities.MovieDetail
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val remote: OMDbRemoteDataSource,
    private val local: LocalDataSource
) : MovieRepository {

    override fun searchMovies(query: String): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        val response = remote.search(query)
        if (response.response == "True" && response.search != null) {
            emit(Result.Success(response.search.map { it.toEntity() }))
        } else {
            emit(Result.Error(response.error ?: "No results"))
        }
    }.catch { e -> emit(Result.Error(e.message ?: "Unknown error", e)) }

    override fun getMovieDetail(imdbID: String): Flow<Result<MovieDetail>> = flow {
        emit(Result.Loading)
        val response = remote.getDetail(imdbID)
        if (response.response == "True") {
            emit(Result.Success(response.toEntity()))
        } else {
            emit(Result.Error(response.error ?: "Detail not found"))
        }
    }.catch { e -> emit(Result.Error(e.message ?: "Unknown error", e)) }

    override fun getFavorites(): Flow<List<Movie>> = local.getFavorites()

    override fun toggleFavorite(movie: Movie): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        // check if exists then delete else insert
        // For scaffold, naive toggle: try insert, if fails delete
        // Real logic in Fase 5 will query isFavorite first
        try {
            local.insertFavorite(movie)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            // if insert fails, try delete
            try {
                local.deleteFavorite(movie.imdbID)
                emit(Result.Success(Unit))
            } catch (de: Exception) {
                emit(Result.Error(de.message ?: "Toggle failed", de))
            }
        }
    }.catch { e -> emit(Result.Error(e.message ?: "Toggle error", e)) }

    override fun isFavorite(imdbID: String): Flow<Boolean> = local.isFavorite(imdbID)
}

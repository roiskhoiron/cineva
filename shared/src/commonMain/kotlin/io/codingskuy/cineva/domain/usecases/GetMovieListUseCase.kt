package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.entities.PaginatedMovies
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetMovieListUseCase(
    private val repository: MovieRepository
) {
    companion object {
        // Curated IMDb Top - OMDb i= lookups (10 titles, diverse genres/years)
        val TRENDING_IDS = listOf(
            "tt0111161", // The Shawshank Redemption
            "tt0068646", // The Godfather
            "tt0468569", // The Dark Knight
            "tt0167260", // LOTR: Return of the King
            "tt0109830", // Forrest Gump
            "tt0137523", // Fight Club
            "tt0110357", // The Lion King
            "tt0133093", // The Matrix
            "tt0114814", // The Usual Suspects
            "tt0120737"  // LOTR: Fellowship
        )
    }

    operator fun invoke(page: Int = 1, pageSize: Int = 10): Flow<Result<PaginatedMovies>> = flow {
        emit(Result.Loading)
        try {
            val movies = coroutineScope {
                TRENDING_IDS.map { imdbID ->
                    async {
                        val result = repository.getMovieDetail(imdbID).first { it !is Result.Loading }
                        when (result) {
                            is Result.Success -> Movie(
                                imdbID = result.data.imdbID,
                                title = result.data.title,
                                year = result.data.year,
                                poster = result.data.poster,
                                type = result.data.type
                            )
                            else -> null
                        }
                    }
                }.awaitAll().filterNotNull()
            }
            if (movies.isNotEmpty()) {
                // Paginate trending list
                val startIndex = (page - 1) * pageSize
                val endIndex = minOf(startIndex + pageSize, movies.size)
                val pageMovies = if (startIndex < movies.size) movies.subList(startIndex, endIndex) else emptyList()
                emit(
                    Result.Success(
                        PaginatedMovies(
                            movies = pageMovies,
                            totalResults = TRENDING_IDS.size,
                            page = page,
                            hasMore = endIndex < movies.size
                        )
                    )
                )
            } else {
                // Fallback: try generic search s=movie (10 per page) if trending fails (quota)
                val fallback = repository.searchMovies("movie", page).first { it !is Result.Loading }
                when (fallback) {
                    is Result.Success -> emit(fallback)
                    is Result.Error -> emit(Result.Success(PaginatedMovies(emptyList(), 0, page, false)))
                    else -> emit(Result.Error("No movies"))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Failed to load trending", e))
        }
    }
}

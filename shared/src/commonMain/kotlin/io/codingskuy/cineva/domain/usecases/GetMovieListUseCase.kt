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
            // For pagination beyond first page, fallback to generic s=movie search
            // Trending list is single page (10), page>1 returns empty with hasMore=false
            if (page > 1) {
                emit(Result.Success(PaginatedMovies(emptyList(), TRENDING_IDS.size, page, false)))
                return@flow
            }
            val movies = coroutineScope {
                TRENDING_IDS.map { imdbID ->
                    async {
                        val result = repository.getMovieDetail(imdbID).first()
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
            // If some lookups failed (quota), fallback to search("movie")
            val finalMovies = if (movies.isEmpty()) {
                // Fallback: try generic search
                val fallback = repository.searchMovies("movie", page).first()
                when (fallback) {
                    is Result.Success -> fallback.data.movies
                    else -> emptyList()
                }
            } else movies

            emit(
                Result.Success(
                    PaginatedMovies(
                        movies = finalMovies,
                        totalResults = TRENDING_IDS.size,
                        page = page,
                        hasMore = false // trending is single page
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Failed to load trending", e))
        }
    }
}

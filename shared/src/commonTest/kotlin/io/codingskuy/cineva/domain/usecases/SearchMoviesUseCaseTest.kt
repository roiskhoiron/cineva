package io.codingskuy.cineva.domain.usecases

import app.cash.turbine.test
import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private class FakeMovieRepository(
    private val searchResult: List<Movie> = emptyList()
) : MovieRepository {
    override fun searchMovies(query: String, page: Int): Flow<Result<io.codingskuy.cineva.domain.entities.PaginatedMovies>> =
        flowOf(Result.Success(io.codingskuy.cineva.domain.entities.PaginatedMovies(searchResult, searchResult.size, page, false)))
    override fun getMovieDetail(imdbID: String): Flow<Result<io.codingskuy.cineva.domain.entities.MovieDetail>> = flowOf(Result.Error("not implemented"))
    override fun getFavorites(): Flow<List<Movie>> = flowOf(emptyList())
    override fun toggleFavorite(movie: Movie): Flow<Result<Unit>> = flowOf(Result.Success(Unit))
    override fun isFavorite(imdbID: String): Flow<Boolean> = flowOf(false)
}

class SearchMoviesUseCaseTest {
    @Test
    fun `blank query returns empty list`() = runTest {
        val repo = FakeMovieRepository()
        val useCase = SearchMoviesUseCase(repo)
        useCase("").test {
            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertEquals(0, (result as Result.Success).data.movies.size)
            awaitComplete()
        }
    }

    @Test
    fun `non-blank query delegates to repository`() = runTest {
        val movies = listOf(Movie("tt123", "Batman", "2022", "https://poster", "movie"))
        val repo = FakeMovieRepository(searchResult = movies)
        val useCase = SearchMoviesUseCase(repo)
        useCase("batman").test {
            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertEquals(1, (result as Result.Success).data.movies.size)
            awaitComplete()
        }
    }

    @Test
    fun `whitespace trimmed before search`() = runTest {
        val movies = listOf(Movie("tt123", "Batman", "2022", "", "movie"))
        val repo = FakeMovieRepository(movies)
        val useCase = SearchMoviesUseCase(repo)
        useCase("  batman  ").test {
            assertTrue(awaitItem() is Result.Success)
            awaitComplete()
        }
    }
}

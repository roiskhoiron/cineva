package io.codingskuy.cineva.domain.usecases

import app.cash.turbine.test
import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

private class FakeToggleRepo : MovieRepository {
    override fun searchMovies(query: String, page: Int): Flow<Result<io.codingskuy.cineva.domain.entities.PaginatedMovies>> = flowOf(Result.Success(io.codingskuy.cineva.domain.entities.PaginatedMovies(emptyList(), 0, page, false)))
    override fun getMovieDetail(imdbID: String): Flow<Result<io.codingskuy.cineva.domain.entities.MovieDetail>> = flowOf(Result.Error("not implemented"))
    override fun getFavorites(): Flow<List<Movie>> = flowOf(emptyList())
    override fun toggleFavorite(movie: Movie): Flow<Result<Unit>> = flowOf(Result.Success(Unit))
    override fun isFavorite(imdbID: String): Flow<Boolean> = flowOf(false)
}

class ToggleFavoriteUseCaseTest {
    @Test
    fun `toggle favorite delegates to repository`() = runTest {
        val repo = FakeToggleRepo()
        val useCase = ToggleFavoriteUseCase(repo)
        val movie = Movie("tt123", "Batman", "2022", "", "movie")
        useCase(movie).test {
            assertTrue(awaitItem() is Result.Success)
            awaitComplete()
        }
    }
}

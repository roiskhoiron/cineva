package io.codingskuy.cineva.domain.usecases

import io.codingskuy.cineva.domain.entities.MovieDetail
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    operator fun invoke(imdbID: String): Flow<Result<MovieDetail>> {
        require(imdbID.isNotBlank()) { "imdbID cannot be blank" }
        return repository.getMovieDetail(imdbID)
    }
}

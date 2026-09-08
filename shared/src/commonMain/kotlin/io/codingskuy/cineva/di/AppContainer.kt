package io.codingskuy.cineva.di

import io.codingskuy.cineva.data.datasources.local.DatabaseDriverFactory
import io.codingskuy.cineva.data.datasources.local.LocalDataSource
import io.codingskuy.cineva.data.datasources.remote.OMDbRemoteDataSource
import io.codingskuy.cineva.data.repositories.MovieRepositoryImpl
import io.codingskuy.cineva.domain.repositories.MovieRepository
import io.codingskuy.cineva.domain.usecases.GetFavoritesUseCase
import io.codingskuy.cineva.domain.usecases.GetMovieDetailUseCase
import io.codingskuy.cineva.domain.usecases.GetMovieListUseCase
import io.codingskuy.cineva.domain.usecases.IsFavoriteUseCase
import io.codingskuy.cineva.domain.usecases.SearchMoviesUseCase
import io.codingskuy.cineva.domain.usecases.ToggleFavoriteUseCase

class AppContainer(
    driverFactory: DatabaseDriverFactory,
    apiKey: String = ""
) {
    private val remote = OMDbRemoteDataSource(apiKey = apiKey)
    private val local = LocalDataSource(driverFactory)
    private val repository: MovieRepository = MovieRepositoryImpl(remote, local)

    val searchMoviesUseCase = SearchMoviesUseCase(repository)
    val getMovieListUseCase = GetMovieListUseCase(repository)
    val getMovieDetailUseCase = GetMovieDetailUseCase(repository)
    val getFavoritesUseCase = GetFavoritesUseCase(repository)
    val toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
    val isFavoriteUseCase = IsFavoriteUseCase(repository)
}

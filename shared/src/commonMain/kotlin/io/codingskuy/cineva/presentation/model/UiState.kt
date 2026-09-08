package io.codingskuy.cineva.presentation.model

import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.entities.MovieDetail

sealed interface MovieListUiState {
    data object Loading : MovieListUiState
    data class Success(
        val movies: List<Movie>,
        val query: String = "",
        val page: Int = 1,
        val hasMore: Boolean = false,
        val isLoadingMore: Boolean = false
    ) : MovieListUiState
    data class Error(val message: String) : MovieListUiState
}

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(val detail: MovieDetail, val isFavorite: Boolean = false) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}

data class FavoriteUiState(val favorites: List<Movie> = emptyList())

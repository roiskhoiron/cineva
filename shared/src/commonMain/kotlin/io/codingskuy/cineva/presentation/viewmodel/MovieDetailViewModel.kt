package io.codingskuy.cineva.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.codingskuy.cineva.domain.repositories.Result
import io.codingskuy.cineva.domain.usecases.GetMovieDetailUseCase
import io.codingskuy.cineva.domain.usecases.IsFavoriteUseCase
import io.codingskuy.cineva.domain.usecases.ToggleFavoriteUseCase
import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.presentation.model.MovieDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    fun loadDetail(imdbID: String) {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            getMovieDetailUseCase(imdbID).collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> MovieDetailUiState.Success(result.data, false)
                    is Result.Error -> MovieDetailUiState.Error(result.message)
                    Result.Loading -> MovieDetailUiState.Loading
                }
                if (result is Result.Success) {
                    // check favorite status
                    isFavoriteUseCase(imdbID).collect { fav ->
                        val current = _uiState.value
                        if (current is MovieDetailUiState.Success) {
                            _uiState.value = current.copy(isFavorite = fav)
                        }
                    }
                }
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie).collect { result ->
                if (result is Result.Success) {
                    val current = _uiState.value
                    if (current is MovieDetailUiState.Success) {
                        _uiState.value = current.copy(isFavorite = !current.isFavorite)
                    }
                }
            }
        }
    }
}

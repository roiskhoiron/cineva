package io.codingskuy.cineva.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.codingskuy.cineva.domain.repositories.Result
import io.codingskuy.cineva.domain.usecases.SearchMoviesUseCase
import io.codingskuy.cineva.presentation.model.MovieListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        search(newQuery)
    }

    fun search(query: String) {
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            searchMoviesUseCase(query).collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> MovieListUiState.Success(result.data, query)
                    is Result.Error -> MovieListUiState.Error(result.message)
                    Result.Loading -> MovieListUiState.Loading
                }
            }
        }
    }

    fun loadDefault() = search("batman")
}

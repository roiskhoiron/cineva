package io.codingskuy.cineva.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.codingskuy.cineva.domain.usecases.SearchMoviesUseCase
import io.codingskuy.cineva.presentation.model.MovieListUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Success(emptyList()))
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {
        _query
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .onEach { q ->
                if (q.isBlank()) {
                    _uiState.value = MovieListUiState.Success(emptyList(), q)
                } else {
                    _uiState.value = MovieListUiState.Loading
                    searchMoviesUseCase(q).collect { result ->
                        _uiState.value = when (result) {
                            is io.codingskuy.cineva.domain.repositories.Result.Success -> MovieListUiState.Success(result.data, q)
                            is io.codingskuy.cineva.domain.repositories.Result.Error -> MovieListUiState.Error(result.message)
                            io.codingskuy.cineva.domain.repositories.Result.Loading -> MovieListUiState.Loading
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun clear() {
        _query.value = ""
        _uiState.value = MovieListUiState.Success(emptyList(), "")
    }
}

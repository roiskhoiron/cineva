package io.codingskuy.cineva.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.codingskuy.cineva.domain.repositories.Result
import io.codingskuy.cineva.domain.usecases.GetMovieListUseCase
import io.codingskuy.cineva.domain.usecases.SearchMoviesUseCase
import io.codingskuy.cineva.presentation.model.MovieListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var currentPage = 1
    private var currentQuery = ""
    private var hasMore = false
    private var isLoadingMore = false

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        search(newQuery)
    }

    fun search(query: String) {
        currentQuery = query
        currentPage = 1
        hasMore = false
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            searchMoviesUseCase(query, page = 1).collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> {
                        hasMore = result.data.hasMore
                        MovieListUiState.Success(
                            movies = result.data.movies,
                            query = query,
                            page = result.data.page,
                            hasMore = result.data.hasMore,
                            isLoadingMore = false
                        )
                    }
                    is Result.Error -> MovieListUiState.Error(result.message)
                    Result.Loading -> MovieListUiState.Loading
                }
            }
        }
    }

    fun loadMore() {
        if (isLoadingMore || !hasMore) return
        val prevSuccess = _uiState.value as? MovieListUiState.Success ?: return
        val nextPage = currentPage + 1
        isLoadingMore = true
        _uiState.value = prevSuccess.copy(isLoadingMore = true)
        viewModelScope.launch {
            searchMoviesUseCase(currentQuery, page = nextPage).collect { result ->
                isLoadingMore = false
                when (result) {
                    is Result.Success -> {
                        hasMore = result.data.hasMore
                        currentPage = result.data.page
                        _uiState.value = MovieListUiState.Success(
                            movies = prevSuccess.movies + result.data.movies,
                            query = currentQuery,
                            page = nextPage,
                            hasMore = result.data.hasMore,
                            isLoadingMore = false
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = prevSuccess.copy(isLoadingMore = false)
                    }
                    Result.Loading -> Unit
                }
            }
        }
    }

    fun onLoadMore() = loadMore()

    fun loadDefault() {
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            getMovieListUseCase(page = 1).collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> MovieListUiState.Success(
                        movies = result.data.movies,
                        query = "",
                        page = result.data.page,
                        hasMore = result.data.hasMore,
                        isLoadingMore = false
                    )
                    is Result.Error -> MovieListUiState.Error(result.message)
                    Result.Loading -> MovieListUiState.Loading
                }
                if (result is Result.Success) {
                    hasMore = result.data.hasMore
                    currentPage = result.data.page
                    currentQuery = ""
                }
            }
        }
    }
}

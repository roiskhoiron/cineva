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
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Success(emptyList()))
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var hasMore = false
    private var isLoadingMore = false
    private var currentQuery = ""

    init {
        _query
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .onEach { q ->
                currentQuery = q
                currentPage = 1
                hasMore = false
                if (q.isBlank()) {
                    _uiState.value = MovieListUiState.Success(emptyList(), q, hasMore = false)
                } else {
                    _uiState.value = MovieListUiState.Loading
                    searchMoviesUseCase(q, page = 1).collect { result ->
                        _uiState.value = when (result) {
                            is io.codingskuy.cineva.domain.repositories.Result.Success -> {
                                hasMore = result.data.hasMore
                                currentPage = result.data.page
                                MovieListUiState.Success(
                                    movies = result.data.movies,
                                    query = q,
                                    page = result.data.page,
                                    hasMore = result.data.hasMore,
                                    isLoadingMore = false
                                )
                            }
                            is io.codingskuy.cineva.domain.repositories.Result.Error -> MovieListUiState.Error(result.message)
                            io.codingskuy.cineva.domain.repositories.Result.Loading -> MovieListUiState.Loading
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadMore() {
        if (isLoadingMore || !hasMore) return
        val prev = _uiState.value as? MovieListUiState.Success ?: return
        val nextPage = currentPage + 1
        isLoadingMore = true
        _uiState.value = prev.copy(isLoadingMore = true)
        viewModelScope.launch {
            searchMoviesUseCase(currentQuery, page = nextPage).collect { result ->
                isLoadingMore = false
                when (result) {
                    is io.codingskuy.cineva.domain.repositories.Result.Success -> {
                        hasMore = result.data.hasMore
                        currentPage = result.data.page
                        _uiState.value = MovieListUiState.Success(
                            movies = prev.movies + result.data.movies,
                            query = currentQuery,
                            page = nextPage,
                            hasMore = result.data.hasMore,
                            isLoadingMore = false
                        )
                    }
                    is io.codingskuy.cineva.domain.repositories.Result.Error -> {
                        _uiState.value = prev.copy(isLoadingMore = false)
                    }
                    io.codingskuy.cineva.domain.repositories.Result.Loading -> Unit
                }
            }
        }
    }

    fun onLoadMore() = loadMore()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun clear() {
        _query.value = ""
        _uiState.value = MovieListUiState.Success(emptyList(), "")
    }
}

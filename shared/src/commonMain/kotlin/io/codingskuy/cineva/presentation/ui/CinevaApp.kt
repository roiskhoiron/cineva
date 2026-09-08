package io.codingskuy.cineva.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.codingskuy.cineva.di.AppContainer
import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.presentation.viewmodel.FavoriteViewModel
import io.codingskuy.cineva.presentation.viewmodel.MovieDetailViewModel
import io.codingskuy.cineva.presentation.viewmodel.MovieListViewModel
import io.codingskuy.cineva.presentation.viewmodel.SearchViewModel

@Composable
fun CinevaApp(container: AppContainer) {
    MaterialTheme {
        var selectedTab by remember { mutableStateOf(0) }
        var selectedMovie by remember { mutableStateOf<Movie?>(null) }

        // Detail screen takes over full UI when a movie is selected
        if (selectedMovie != null) {
            val movie = selectedMovie!!
            val detailVm = remember(movie.imdbID) {
                MovieDetailViewModel(
                    container.getMovieDetailUseCase,
                    container.toggleFavoriteUseCase,
                    container.isFavoriteUseCase
                )
            }
            LaunchedEffect(movie.imdbID) { detailVm.loadDetail(movie.imdbID) }
            val detailState by detailVm.uiState.collectAsState()
            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .safeDrawingPadding()
                        .statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { selectedMovie = null }) { Text("← Back") }
                        Text("Detail", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 8.dp))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        when (val s = detailState) {
                            is io.codingskuy.cineva.presentation.model.MovieDetailUiState.Loading ->
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Loading detail...") }
                            is io.codingskuy.cineva.presentation.model.MovieDetailUiState.Error ->
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(s.message, modifier = Modifier.padding(16.dp)) }
                            is io.codingskuy.cineva.presentation.model.MovieDetailUiState.Success -> MovieDetailView(
                                detail = s.detail,
                                isFavorite = s.isFavorite,
                                onToggleFavorite = {
                                    // toggle using original Movie or from detail
                                    val toToggle = Movie(
                                        imdbID = s.detail.imdbID,
                                        title = s.detail.title,
                                        year = s.detail.year,
                                        poster = s.detail.poster,
                                        type = s.detail.type
                                    )
                                    detailVm.toggleFavorite(toToggle)
                                }
                            )
                        }
                    }
                }
            }
            return@MaterialTheme
        }

        // Scaffold handles WindowInsets for edge-to-edge (MainActivity enableEdgeToEdge)
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .safeDrawingPadding()
                    .statusBarsPadding()
            ) {
                PrimaryTabRow(selectedTabIndex = selectedTab) {
                    Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Movies") })
                    Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Search") })
                    Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Favorites") })
                }
                Box(modifier = Modifier.weight(1f)) {
                    when (selectedTab) {
                        0 -> {
                            val vm = remember { MovieListViewModel(container.getMovieListUseCase, container.searchMoviesUseCase) }
                            LaunchedEffect(vm) { vm.loadDefault() }
                            val state by vm.uiState.collectAsState()
                            when (val s = state) {
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Success -> MovieListView(
                                    movies = s.movies,
                                    onMovieClick = { selectedMovie = it },
                                    hasMore = s.hasMore,
                                    isLoadingMore = s.isLoadingMore,
                                    onLoadMore = { vm.onLoadMore() }
                                )
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Error -> Text(s.message, modifier = Modifier.padding(16.dp))
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Loading movies...") }
                            }
                        }
                    1 -> {
                        val vm = remember { SearchViewModel(container.searchMoviesUseCase) }
                        val query by vm.query.collectAsState()
                        val state by vm.uiState.collectAsState()
                        Column {
                            SearchView(query = query, onQueryChange = vm::onQueryChange, onClear = vm::clear)
                            when (val s = state) {
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Success -> MovieListView(
                                    movies = s.movies,
                                    onMovieClick = { selectedMovie = it },
                                    hasMore = s.hasMore,
                                    isLoadingMore = s.isLoadingMore,
                                    onLoadMore = { vm.onLoadMore() }
                                )
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Error -> Text(s.message, modifier = Modifier.padding(8.dp))
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Searching...") }
                            }
                        }
                    }
                    2 -> {
                        val vm = remember { FavoriteViewModel(container.getFavoritesUseCase) }
                        val state by vm.uiState.collectAsState()
                        FavoriteView(favorites = state.favorites, onMovieClick = { selectedMovie = it })
                    }
                }
            }
        }
        }
    }
}

package io.codingskuy.cineva.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.codingskuy.cineva.di.AppContainer
import io.codingskuy.cineva.presentation.viewmodel.FavoriteViewModel
import io.codingskuy.cineva.presentation.viewmodel.MovieListViewModel
import io.codingskuy.cineva.presentation.viewmodel.SearchViewModel

@Composable
fun CinevaApp(container: AppContainer) {
    MaterialTheme {
        var selectedTab by remember { mutableStateOf(0) }
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
                            val vm = remember { MovieListViewModel(container.searchMoviesUseCase) }
                            LaunchedEffect(vm) { vm.loadDefault() }
                            val state by vm.uiState.collectAsState()
                            when (val s = state) {
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Success -> MovieListView(movies = s.movies, onMovieClick = {})
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Error -> Text(s.message, modifier = Modifier.padding(16.dp))
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) { Text("Loading movies...") }
                            }
                        }
                    1 -> {
                        val vm = remember { SearchViewModel(container.searchMoviesUseCase) }
                        val query by vm.query.collectAsState()
                        val state by vm.uiState.collectAsState()
                        Column {
                            SearchView(query = query, onQueryChange = vm::onQueryChange, onClear = vm::clear)
                            when (val s = state) {
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Success -> MovieListView(movies = s.movies, onMovieClick = {})
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Error -> Text(s.message)
                                is io.codingskuy.cineva.presentation.model.MovieListUiState.Loading -> Text("Searching...")
                            }
                        }
                    }
                    2 -> {
                        val vm = remember { FavoriteViewModel(container.getFavoritesUseCase) }
                        val state by vm.uiState.collectAsState()
                        FavoriteView(favorites = state.favorites, onMovieClick = {})
                    }
                }
            }
        }
        }
    }
}

package io.codingskuy.cineva.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.codingskuy.cineva.domain.entities.Movie

@Composable
fun FavoriteView(
    favorites: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (favorites.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No favorites yet. Tap ♥ on detail.", modifier = Modifier.padding(16.dp))
        }
    } else {
        MovieListView(movies = favorites, onMovieClick = onMovieClick, modifier = modifier)
    }
}

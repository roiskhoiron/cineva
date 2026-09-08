package io.codingskuy.cineva.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.codingskuy.cineva.domain.entities.Movie
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun MovieListView(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    hasMore: Boolean = false,
    isLoadingMore: Boolean = false,
    onLoadMore: () -> Unit = {}
) {
    if (movies.isEmpty() && !isLoadingMore) {
        Text("No movies. Try searching 'batman'.", modifier = modifier.padding(16.dp))
        return
    }
    val listState = rememberLazyListState()
    // Trigger pagination when near end
    LaunchedEffect(listState, movies.size, hasMore) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .map { it ?: 0 }
            .distinctUntilChanged()
            .collect { lastVisible ->
                if (hasMore && !isLoadingMore && lastVisible >= movies.size - 3 && movies.isNotEmpty()) {
                    onLoadMore()
                }
            }
    }
    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies, key = { it.imdbID }) { movie ->
            MovieRow(movie = movie, onClick = { onMovieClick(movie) })
        }
        if (isLoadingMore) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }
        if (!hasMore && movies.isNotEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center) {
                    Text("No more results", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun MovieRow(movie: Movie, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = movie.poster,
                contentDescription = movie.title,
                modifier = Modifier.size(60.dp, 90.dp)
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                Text(movie.year, style = MaterialTheme.typography.bodySmall)
                Text(movie.type, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

package io.codingskuy.cineva.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.codingskuy.cineva.domain.entities.MovieDetail

@Composable
fun MovieDetailView(
    detail: MovieDetail,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
        AsyncImage(
            model = detail.poster,
            contentDescription = detail.title,
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(detail.title, style = MaterialTheme.typography.headlineSmall)
        Text("${detail.year} • ${detail.runtime} • ${detail.rated}", style = MaterialTheme.typography.bodySmall)
        Text("IMDb: ${detail.imdbRating}", style = MaterialTheme.typography.bodyMedium)
        Text("Genre: ${detail.genre}", style = MaterialTheme.typography.bodySmall)
        Text("Director: ${detail.director}", style = MaterialTheme.typography.bodySmall)
        Text("Actors: ${detail.actors}", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        Text(detail.plot, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onToggleFavorite, modifier = Modifier.fillMaxWidth()) {
            Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
        }
    }
}

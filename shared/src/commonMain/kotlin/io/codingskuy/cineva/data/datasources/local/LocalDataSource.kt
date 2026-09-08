package io.codingskuy.cineva.data.datasources.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.codingskuy.cineva.db.CinevaDatabase
import io.codingskuy.cineva.domain.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(driverFactory: DatabaseDriverFactory) {
    private val database = CinevaDatabase(driverFactory.createDriver())
    private val queries = database.favoriteQueries

    fun getFavorites(): Flow<List<Movie>> =
        queries.selectAll().asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { row ->
                Movie(
                    imdbID = row.imdbID,
                    title = row.title,
                    year = row.year,
                    poster = row.poster,
                    type = row.type
                )
            }
        }

    fun isFavorite(imdbID: String): Flow<Boolean> =
        queries.selectById(imdbID).asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it != null }

    fun insertFavorite(movie: Movie) {
        queries.insertFavorite(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            type = movie.type,
            addedAt = 0L
        )
    }

    fun deleteFavorite(imdbID: String) {
        queries.deleteFavorite(imdbID)
    }
}

package io.codingskuy.cineva.domain.entities

data class Movie(
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val type: String = "movie"
)

data class MovieDetail(
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val imdbRating: String,
    val type: String
)

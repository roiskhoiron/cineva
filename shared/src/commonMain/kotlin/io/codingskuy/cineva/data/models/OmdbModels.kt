package io.codingskuy.cineva.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import io.codingskuy.cineva.domain.entities.Movie
import io.codingskuy.cineva.domain.entities.MovieDetail

@Serializable
data class SearchResponse(
    @SerialName("Search") val search: List<SearchItem>? = null,
    @SerialName("totalResults") val totalResults: String? = null,
    @SerialName("Response") val response: String = "False",
    @SerialName("Error") val error: String? = null
)

@Serializable
data class SearchItem(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("imdbID") val imdbID: String = "",
    @SerialName("Type") val type: String = "movie",
    @SerialName("Poster") val poster: String = ""
)

fun SearchItem.toEntity(): Movie = Movie(
    imdbID = imdbID,
    title = title,
    year = year,
    poster = poster,
    type = type
)

@Serializable
data class DetailResponse(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("Rated") val rated: String = "",
    @SerialName("Released") val released: String = "",
    @SerialName("Runtime") val runtime: String = "",
    @SerialName("Genre") val genre: String = "",
    @SerialName("Director") val director: String = "",
    @SerialName("Writer") val writer: String = "",
    @SerialName("Actors") val actors: String = "",
    @SerialName("Plot") val plot: String = "",
    @SerialName("Poster") val poster: String = "",
    @SerialName("imdbRating") val imdbRating: String = "",
    @SerialName("imdbID") val imdbID: String = "",
    @SerialName("Type") val type: String = "movie",
    @SerialName("Response") val response: String = "False",
    @SerialName("Error") val error: String? = null
)

fun DetailResponse.toEntity(): MovieDetail = MovieDetail(
    imdbID = imdbID,
    title = title,
    year = year,
    rated = rated,
    released = released,
    runtime = runtime,
    genre = genre,
    director = director,
    writer = writer,
    actors = actors,
    plot = plot,
    poster = poster,
    imdbRating = imdbRating,
    type = type
)

package io.codingskuy.cineva.domain.entities

data class PaginatedMovies(
    val movies: List<Movie>,
    val totalResults: Int,
    val page: Int,
    val hasMore: Boolean
) {
    companion object {
        fun empty(page: Int = 1) = PaginatedMovies(emptyList(), 0, page, false)
    }
}

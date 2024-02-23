package com.example.moviecatalogue.movies.data.remote.respond

data class MoviesDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)
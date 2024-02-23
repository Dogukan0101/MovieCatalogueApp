package com.example.moviecatalogue.movies.presentation.toprated_movies_screen

import com.example.moviecatalogue.movies.domain.model.Movie

data class TopRatedMoviesState(
    val isLoading: Boolean = false,
    val topRatedMoviesPage: Int = 1,
    val topRatedMovies: List<Movie> = emptyList()
    )

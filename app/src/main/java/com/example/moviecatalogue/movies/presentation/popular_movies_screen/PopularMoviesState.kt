package com.example.moviecatalogue.movies.presentation.popular_movies_screen

import com.example.moviecatalogue.movies.domain.model.Movie

data class PopularMoviesState(
    val isLoading: Boolean = false,
    val popularMoviesPage: Int = 1,
    val popularMovies: List<Movie> = emptyList()
    )
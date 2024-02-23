package com.example.moviecatalogue.movies.presentation.home_screen

import com.example.moviecatalogue.movies.domain.model.Movie

data class HomeScreenState(
    val currentScreen: String = "popularMovie",
    val watchedMovies: List<Movie> = emptyList(),
    val isWatchedMoviesLoading: Boolean = false
)
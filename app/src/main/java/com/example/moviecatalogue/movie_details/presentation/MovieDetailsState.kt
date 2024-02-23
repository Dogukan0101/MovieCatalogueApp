package com.example.moviecatalogue.movie_details.presentation

import com.example.moviecatalogue.movies.domain.model.Movie

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val isMovieWatched: Boolean = false
)


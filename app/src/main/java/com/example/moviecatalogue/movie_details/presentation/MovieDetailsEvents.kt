package com.example.moviecatalogue.movie_details.presentation

import com.example.moviecatalogue.movies.domain.model.Movie

sealed class MovieDetailsEvent{
    data class deleteWatchedMovie(val movie: Movie): MovieDetailsEvent()
    data class upsertWatchedMovie(val movie: Movie): MovieDetailsEvent()
}

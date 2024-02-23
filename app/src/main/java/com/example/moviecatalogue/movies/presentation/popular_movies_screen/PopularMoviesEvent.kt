package com.example.moviecatalogue.movies.presentation.popular_movies_screen

sealed interface PopularMoviesEvent{
    data class Paginate(val category: String): PopularMoviesEvent
}

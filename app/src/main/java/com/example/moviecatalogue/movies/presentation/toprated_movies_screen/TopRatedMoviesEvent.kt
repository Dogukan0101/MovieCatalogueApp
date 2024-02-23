package com.example.moviecatalogue.movies.presentation.toprated_movies_screen


sealed interface TopRatedMoviesEvent{
    data class Paginate(val category: String): TopRatedMoviesEvent
}
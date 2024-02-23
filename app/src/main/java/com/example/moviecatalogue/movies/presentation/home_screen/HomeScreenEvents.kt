package com.example.moviecatalogue.movies.presentation.home_screen


sealed interface HomeScreenEvents{
    data class Navigate(val screenRoute: String): HomeScreenEvents
    object UpdateWatchedMovies:HomeScreenEvents
}
package com.example.moviecatalogue.movies.util

sealed class Screen(val route: String) {
    object Home : Screen("main")
    object WatchedMovies: Screen("watchedMovie")
    object PopularMovies : Screen("popularMovie")
    object TopRatedMovies : Screen("topRatedMovie")
    object MovieDetails : Screen("detailsOfTheMovie")
}

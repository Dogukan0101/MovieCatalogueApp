package com.example.moviecatalogue.watched_movies.domain.repository

import com.example.moviecatalogue.movies.domain.model.Movie
import com.example.moviecatalogue.watched_movies.data.local.watched_movie.WatchedMovieEntity
import kotlinx.coroutines.flow.Flow

interface WatchedMoviesRepository {

    fun getAllWatchedMovies():Flow<List<Movie>>

    fun getWatchedMovieById(id:Int):Flow<WatchedMovieEntity?>

    fun upsertWatchedMovie(watchedMovieEntity: WatchedMovieEntity):Unit

    fun deleteWatchedMovie(watchedMovieEntity: WatchedMovieEntity):Unit

}
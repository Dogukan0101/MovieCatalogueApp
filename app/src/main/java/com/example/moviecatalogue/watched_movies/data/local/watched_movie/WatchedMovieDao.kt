package com.example.moviecatalogue.watched_movies.data.local.watched_movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WatchedMovieDao {

    @Query("select * from WatchedMovieEntity")
    fun getAllWatchedMovies():List<WatchedMovieEntity>

    @Query("select * from WatchedMovieEntity where id = :id")
    fun getWatchedMovieById(id:Int):WatchedMovieEntity?


    @Upsert
    fun upsertWatchedMovie(watchedMovieEntity: WatchedMovieEntity)

    @Delete
    fun deleteWatchedMovie(watchedMovieEntity: WatchedMovieEntity)
}
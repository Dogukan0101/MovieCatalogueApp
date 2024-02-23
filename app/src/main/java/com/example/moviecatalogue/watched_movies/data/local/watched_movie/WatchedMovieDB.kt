package com.example.moviecatalogue.watched_movies.data.local.watched_movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WatchedMovieEntity::class],
    version = 1
)
abstract class WatchedMovieDB():RoomDatabase(){
    abstract val watchedMovieDao: WatchedMovieDao
}
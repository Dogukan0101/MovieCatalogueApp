package com.example.moviecatalogue.movies.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDB:RoomDatabase() {

    abstract val movieDao:MovieDao
}
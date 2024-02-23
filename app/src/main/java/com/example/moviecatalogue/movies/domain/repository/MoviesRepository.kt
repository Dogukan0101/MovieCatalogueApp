package com.example.moviecatalogue.movies.domain.repository

import com.example.moviecatalogue.movies.domain.model.Movie
import com.example.moviecatalogue.movies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(
        fetchFromRemote:Boolean,
        category:String,
        page:Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovieById(id:Int):Flow<Resource<Movie>>

}
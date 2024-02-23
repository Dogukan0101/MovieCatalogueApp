package com.example.moviecatalogue.movies.data.mappers

import com.example.moviecatalogue.movies.data.local.movie.MovieEntity
import com.example.moviecatalogue.movies.data.remote.respond.MovieDto
import com.example.moviecatalogue.movies.domain.model.Movie

fun MovieDto.toMovieEntity(
    categoryA: String
):MovieEntity{
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        genre_ids = try{
              genre_ids?.joinToString { "," } ?: "-1,-1,-1"
                       }catch (e: Exception){
                                "Exception"
                                            },
        original_language = original_language ?: "",
        original_title = original_title ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        video = video ?: false,
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        category = categoryA
    )
}

fun MovieEntity.toMovie(
    categoryA: String
):Movie{
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = try {
             genre_ids.split(",").map { it.toInt() }
        }catch (e:Exception){
            listOf(-1,-1,-1)
                            },
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        id = id,
        category = categoryA
    )
}
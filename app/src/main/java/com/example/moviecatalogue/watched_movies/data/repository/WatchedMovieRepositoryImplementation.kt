package com.example.moviecatalogue.watched_movies.data.repository

import com.example.moviecatalogue.movies.domain.model.Movie
import com.example.moviecatalogue.watched_movies.data.local.watched_movie.WatchedMovieDB
import com.example.moviecatalogue.watched_movies.data.local.watched_movie.WatchedMovieEntity
import com.example.moviecatalogue.watched_movies.data.mappers.toMovie
import com.example.moviecatalogue.watched_movies.domain.repository.WatchedMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WatchedMovieRepositoryImplementation @Inject constructor(
    val watchedMovieDB: WatchedMovieDB
):WatchedMoviesRepository{

    override fun getAllWatchedMovies(): Flow<List<Movie>> {
        return flow {

            val watchedMovies = watchedMovieDB.watchedMovieDao.getAllWatchedMovies()

            emit(watchedMovies.map {  watchedMovieEntity ->
                watchedMovieEntity.toMovie()
            })

            return@flow
        }
    }

    override fun getWatchedMovieById(id: Int): Flow<WatchedMovieEntity?> {
        return flow {
            val watchedMovieById = watchedMovieDB.watchedMovieDao.getWatchedMovieById(id)
            if(watchedMovieById != null){
                emit(watchedMovieById)
            }else{
                emit(null)
            }
            return@flow
        }
    }


    override fun upsertWatchedMovie(watchedMovieEntity: WatchedMovieEntity) {
        watchedMovieDB.watchedMovieDao.upsertWatchedMovie(watchedMovieEntity)
    }

    override fun deleteWatchedMovie(watchedMovieEntity: WatchedMovieEntity) {
        watchedMovieDB.watchedMovieDao.deleteWatchedMovie(watchedMovieEntity)
    }


}
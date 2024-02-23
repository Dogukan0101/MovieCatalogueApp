package com.example.moviecatalogue.movies.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.moviecatalogue.movies.data.local.movie.MovieDB
import com.example.moviecatalogue.movies.data.mappers.toMovie
import com.example.moviecatalogue.movies.data.mappers.toMovieEntity
import com.example.moviecatalogue.movies.data.remote.MovieApi
import com.example.moviecatalogue.movies.domain.model.Movie
import com.example.moviecatalogue.movies.domain.repository.MoviesRepository
import com.example.moviecatalogue.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImplementation @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDB: MovieDB
):MoviesRepository{

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMovies(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMovies = movieDB.movieDao.getMoviesByCategory(category)

            if(localMovies.isNotEmpty() && !fetchFromRemote){

                emit(Resource.Success(
                    data = localMovies.map {
                        movieEntity ->  movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val moviesFromApi = try{
                movieApi.getMovies(category,page)
            }
            catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error Occured"))
                return@flow
            }
            catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error Occured"))
                return@flow
            }

            val movieEntities = moviesFromApi.results.let { movieDtos ->
                movieDtos.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDB.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map {
                    it.toMovie(category)
                }
            ))

            emit(Resource.Loading(false))
        }
    }


    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieById = movieDB.movieDao.getMovieById(id)

            if (movieById != null){
                emit(Resource.Success(movieById.toMovie(categoryA = movieById.category)))
            }

            emit(Resource.Loading(false))

            return@flow

        }
    }


}
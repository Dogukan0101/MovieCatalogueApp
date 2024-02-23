package com.example.moviecatalogue.di

import com.example.moviecatalogue.movies.data.repository.MoviesRepositoryImplementation
import com.example.moviecatalogue.movies.domain.repository.MoviesRepository
import com.example.moviecatalogue.watched_movies.data.repository.WatchedMovieRepositoryImplementation
import com.example.moviecatalogue.watched_movies.domain.repository.WatchedMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        moviesRepositoryImplementation: MoviesRepositoryImplementation
    ):MoviesRepository

    @Binds
    @Singleton
    abstract fun bindWatchedMovieRepository(
        watchedMovieRepositoryImplementation: WatchedMovieRepositoryImplementation
    ):WatchedMoviesRepository

}
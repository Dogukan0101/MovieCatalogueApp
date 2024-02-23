package com.example.moviecatalogue.di

import android.app.Application
import androidx.room.Room
import com.example.moviecatalogue.movies.data.local.movie.MovieDB
import com.example.moviecatalogue.movies.data.remote.MovieApi
import com.example.moviecatalogue.watched_movies.data.local.watched_movie.WatchedMovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{


    private val intreceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(intreceptor)
        .build()

    @Singleton
    @Provides
    fun provideMovieApi():MovieApi{
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDB(app:Application):MovieDB{

        return Room.databaseBuilder(
            app,
            MovieDB::class.java,
            "moviedb.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWatchedMovieDB(app: Application):WatchedMovieDB{
        return Room.databaseBuilder(
            app,
            WatchedMovieDB::class.java,
            "watchedmoviedb.db"
        ).build()
    }

}


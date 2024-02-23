package com.example.moviecatalogue.movie_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.movies.domain.repository.MoviesRepository
import com.example.moviecatalogue.movies.util.Resource
import com.example.moviecatalogue.watched_movies.data.mappers.toWatchedMovieEntitiy
import com.example.moviecatalogue.watched_movies.domain.repository.WatchedMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val watchedMoviesRepository: WatchedMoviesRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private var _detailsState = MutableStateFlow(MovieDetailsState())

    val detailsState = _detailsState.asStateFlow()

    val movieId = savedStateHandle.get<Int>("movieId") ?: -1

    init {
        getMovieById(movieId)
        isMovieWatchedById(movieId)
    }

    fun onEvent(event: MovieDetailsEvent){
        when(event){

            is MovieDetailsEvent.deleteWatchedMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    watchedMoviesRepository.deleteWatchedMovie(event.movie.toWatchedMovieEntitiy())
                }
            }

            is MovieDetailsEvent.upsertWatchedMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    watchedMoviesRepository.upsertWatchedMovie(event.movie.toWatchedMovieEntitiy())
                }
            }

        }
    }


    private fun getMovieById(id:Int){
        viewModelScope.launch {

            _detailsState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.getMovieById(id).collectLatest { result ->

                when(result){
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data.let { movie ->
                            _detailsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }

        }
    }

    private fun isMovieWatchedById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            watchedMoviesRepository.getWatchedMovieById(id).collectLatest { result ->
                if(result != null){
                    _detailsState.update {
                        it.copy(
                            isMovieWatched = true
                        )
                    }
                }
            }
        }
    }


}


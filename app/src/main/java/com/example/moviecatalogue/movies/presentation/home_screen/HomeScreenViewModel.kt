package com.example.moviecatalogue.movies.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class HomeScreenViewModel @Inject constructor(
    private val watchedMoviesRepository: WatchedMoviesRepository
):ViewModel(){

    private var _homeScreenState = MutableStateFlow(HomeScreenState())

    val homeScreenState = _homeScreenState.asStateFlow()

    init {
        getWatchedMovies()
    }

    fun onEvent(event: HomeScreenEvents){
        when(event){
            is HomeScreenEvents.Navigate -> {
                _homeScreenState.update {
                    it.copy(
                        currentScreen = event.screenRoute
                    )
                }
            }

            HomeScreenEvents.UpdateWatchedMovies -> {
                getWatchedMovies()
            }
        }
    }

    private fun getWatchedMovies(){
        viewModelScope.launch(Dispatchers.IO) {

            _homeScreenState.update {
                it.copy(
                    isWatchedMoviesLoading = true
                )
            }

            watchedMoviesRepository.getAllWatchedMovies()
                .collectLatest { result ->
                    _homeScreenState.update {
                        it.copy(watchedMovies = result)
                    }
                }

            _homeScreenState.update {
                it.copy(
                    isWatchedMoviesLoading = true
                )
            }
        }

    }


}
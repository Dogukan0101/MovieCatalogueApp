package com.example.moviecatalogue.movies.presentation.toprated_movies_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.movies.domain.repository.MoviesRepository
import com.example.moviecatalogue.movies.util.Category
import com.example.moviecatalogue.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
):ViewModel() {

    private val _topRatedMoviesState = MutableStateFlow(TopRatedMoviesState())
    var topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    init {
        getTopRatedMovies(false)
    }


    fun onEvent(event: TopRatedMoviesEvent){
        when(event){
            is TopRatedMoviesEvent.Paginate -> {
                getTopRatedMovies(true)
            }
        }
    }


    private fun getTopRatedMovies(fetchFromRemote:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _topRatedMoviesState.update {
                it.copy(isLoading = true)
            }
            moviesRepository.getMovies(
                fetchFromRemote,
                Category.TOPRATED,
                _topRatedMoviesState.value.topRatedMoviesPage
            ).collectLatest { result ->

                when(result){

                    is Resource.Error -> {
                        _topRatedMoviesState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _topRatedMoviesState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { topRatedList ->
                            _topRatedMoviesState.update {
                                it.copy(
                                    topRatedMovies = topRatedMoviesState.value.topRatedMovies + topRatedList.shuffled(),
                                    topRatedMoviesPage = topRatedMoviesState.value.topRatedMoviesPage + 1
                                )
                            }
                        }
                    }

                }
            }

        }
    }


}
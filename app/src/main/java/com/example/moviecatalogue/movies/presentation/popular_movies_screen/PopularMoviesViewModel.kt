package com.example.moviecatalogue.movies.presentation.popular_movies_screen

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
class PopularMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
    ):ViewModel() {

    val _popularMoviesState = MutableStateFlow(PopularMoviesState())
    val popularMoviesState = _popularMoviesState.asStateFlow()

    init {
        getPopularMovies(false)
    }


    fun onEvent(event:PopularMoviesEvent){
        when(event){
            is PopularMoviesEvent.Paginate -> {
            getPopularMovies(true)
        }
        }
    }

    private fun getPopularMovies(fetchFromRemote:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _popularMoviesState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.getMovies(
                fetchFromRemote,
                Category.POPULAR,
                popularMoviesState.value.popularMoviesPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _popularMoviesState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _popularMoviesState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _popularMoviesState.update {
                                it.copy(
                                    popularMovies =  popularMoviesState.value.popularMovies + popularList.shuffled(),
                                    popularMoviesPage = popularMoviesState.value.popularMoviesPage + 1
                                )
                            }
                        }
                    }
                }
            }

        }
    }


}
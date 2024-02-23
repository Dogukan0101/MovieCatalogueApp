package com.example.moviecatalogue.watched_movies.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecatalogue.movies.presentation.components.MovieItem
import com.example.moviecatalogue.movies.presentation.home_screen.HomeScreenState

@Composable
fun WatchedMoviesScreen(
    homeScreenState: HomeScreenState,
    navHostController: NavHostController
){


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ){
        items(homeScreenState.watchedMovies.size){ index ->
            MovieItem(
                movie = homeScreenState.watchedMovies[index],
                navHostController = navHostController
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
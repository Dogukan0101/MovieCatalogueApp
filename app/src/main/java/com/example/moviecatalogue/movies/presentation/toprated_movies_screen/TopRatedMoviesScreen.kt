package com.example.moviecatalogue.movies.presentation.toprated_movies_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecatalogue.movies.presentation.components.MovieItem
import com.example.moviecatalogue.movies.util.Category

@Composable
fun TopRatedMoviesScreen(
    topRatedMoviesState: TopRatedMoviesState,
    navHostController: NavHostController,
    onEvent: (TopRatedMoviesEvent) -> Unit
){

    if(topRatedMoviesState.topRatedMovies.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 8.dp)
        ){
            items(topRatedMoviesState.topRatedMovies.size){ index ->
                MovieItem(
                    movie = topRatedMoviesState.topRatedMovies[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (index >= topRatedMoviesState.topRatedMovies.size - 1 && !topRatedMoviesState.isLoading){
                    onEvent(TopRatedMoviesEvent.Paginate(category = Category.TOPRATED))
                }

            }
        }
    }

}



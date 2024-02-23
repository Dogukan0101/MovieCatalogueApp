package com.example.moviecatalogue

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviecatalogue.movie_details.presentation.MovieDetailsScreen
import com.example.moviecatalogue.movies.presentation.home_screen.HomeScreen
import com.example.moviecatalogue.movies.presentation.home_screen.HomeScreenViewModel
import com.example.moviecatalogue.movies.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val homeScreenState = homeScreenViewModel.homeScreenState.collectAsState().value


                NavHost(navController = navController, startDestination = Screen.Home.route) {

                    composable(Screen.Home.route) {
                        HomeScreen(navController,homeScreenState,homeScreenViewModel::onEvent)
                    }

                    composable(
                        Screen.MovieDetails.route + "/{movieId}",
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.IntType }
                        )
                    ) {
                        MovieDetailsScreen(homeScreenViewModel::onEvent)
                    }

                }
            }
        }
    }


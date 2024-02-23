package com.example.moviecatalogue.movies.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalogue.movies.presentation.popular_movies_screen.PopularMoviesScreen
import com.example.moviecatalogue.movies.presentation.popular_movies_screen.PopularMoviesViewModel
import com.example.moviecatalogue.movies.presentation.toprated_movies_screen.TopRatedMoviesScreen
import com.example.moviecatalogue.movies.presentation.toprated_movies_screen.TopRatedMoviesViewModel
import com.example.moviecatalogue.movies.util.Screen
import com.example.moviecatalogue.watched_movies.presentation.WatchedMoviesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenState: HomeScreenState,
    onEvent: (HomeScreenEvents) -> Unit
) {

    val bottomNavController = rememberNavController()

    val popularMoviesViewModel = hiltViewModel<PopularMoviesViewModel>()
    val popularMoviesState = popularMoviesViewModel.popularMoviesState.collectAsState().value

    val topRatedMoviesViewModel = hiltViewModel<TopRatedMoviesViewModel>()
    val topRatedMoviesState = topRatedMoviesViewModel.topRatedMoviesState.collectAsState().value

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    bottomNavController = bottomNavController, onEvent = onEvent
                )
            },
            topBar = {
                TopAppBar(
                    title = {
                    Text(
                        text = if (homeScreenState.currentScreen.equals(Screen.PopularMovies.route)) "Popular Movies"
                        else if (homeScreenState.currentScreen.equals(Screen.TopRatedMovies.route)) "Top Rated Movies"
                        else "Watched Movies",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                NavHost(
                    navController = bottomNavController,
                    startDestination = Screen.PopularMovies.route
                ) {
                    composable(Screen.PopularMovies.route) {
                        PopularMoviesScreen(
                            popularMoviesState = popularMoviesState,
                            navHostController = navController,
                            onEvent = popularMoviesViewModel::onEvent
                        )
                    }
                    composable(Screen.TopRatedMovies.route) {
                        TopRatedMoviesScreen(
                            topRatedMoviesState = topRatedMoviesState,
                            navHostController = navController,
                            onEvent = topRatedMoviesViewModel::onEvent
                        )
                    }
                    composable(Screen.WatchedMovies.route) {
                        WatchedMoviesScreen(
                            homeScreenState = homeScreenState,
                            navHostController = navController
                            )
                    }
                }
            }
        }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onEvent: (HomeScreenEvents) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = "Popular",
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = "Top Rated",
            icon = Icons.Rounded.Star
        ),
        BottomItem(
            title = "Watched",
            icon = Icons.Rounded.AccountCircle
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar() {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {

                        0 -> {
                            onEvent(HomeScreenEvents.Navigate(Screen.PopularMovies.route))
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovies.route)
                        }

                        1 -> {
                            onEvent(HomeScreenEvents.Navigate(Screen.TopRatedMovies.route))
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.TopRatedMovies.route)
                        }

                        2 -> {
                            onEvent(HomeScreenEvents.Navigate(Screen.WatchedMovies.route))
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.WatchedMovies.route)
                        }

                    }

                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }
}

data class BottomItem(
    val title: String, val icon: ImageVector
)

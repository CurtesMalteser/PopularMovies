package com.curtesmalteser.popularmoviesstage1.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.curtesmalteser.popularmoviesstage1.nav.Screen
import com.curtesmalteser.popularmoviesstage1.screen.details.MovieDetailsScreen
import com.curtesmalteser.popularmoviesstage1.screen.details.MovieDetailsViewModel
import com.curtesmalteser.popularmoviesstage1.screen.movieslist.MoviesListScreen
import com.curtesmalteser.popularmoviesstage1.viewmodel.PopularMoviesViewModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.TopRatedMoviesViewModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.favorite.FavoriteMoviesViewModel

/**
 * Created by António Bastião on 13.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
fun NavGraphBuilder.mainNavigation(navController: NavHostController) {
    navigation(startDestination = Screen.Popular.route, route = "main") {
        composable(Screen.Popular.route) {
            val viewModel = hiltViewModel<PopularMoviesViewModel>()
            MoviesListScreen(navController, viewModel)
        }
        composable(Screen.TopRated.route) {
            val viewModel = hiltViewModel<TopRatedMoviesViewModel>()
            MoviesListScreen(navController, viewModel)
        }
        composable(Screen.Favorite.route) {
            val viewModel = hiltViewModel<FavoriteMoviesViewModel>()
            MoviesListScreen(navController, viewModel)
        }
    }
}

fun NavGraphBuilder.movieDetailsNavigation(navController: NavHostController) {
    composable(
        route = Screen.MovieDetails.route,
        arguments = listOf(navArgument("movieId") { type = NavType.LongType })
    ) {
        val viewModel = hiltViewModel<MovieDetailsViewModel>()
        MovieDetailsScreen(navController, viewModel)
    }
}

@Composable
fun NavController.shouldShowBottomBar(): Boolean {
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    return currentDestination in listOf(
        Screen.Popular.route,
        Screen.TopRated.route,
        Screen.Favorite.route
    )
}
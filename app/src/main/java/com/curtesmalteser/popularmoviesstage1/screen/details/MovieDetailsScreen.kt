package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * Created by António Bastião on 21.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel,
) {
    val movieId : Long = navController.currentBackStackEntry
        ?.arguments?.getLong("movieId") ?: 0

    Text(text = "MovieDetailsScreen id: $movieId")
}
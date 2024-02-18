package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult


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

    viewModel.setupMovieDetailsFor(movieId)

    val movieDetails by viewModel.movieDetailsFlow.collectAsStateWithLifecycle(Result.success(MovieDetailsResult.NoDetails))

    movieDetails.fold(
        onSuccess = { movieDetailsResult ->
            when (movieDetailsResult) {
                is MovieDetailsResult.MovieDetailsData ->  Text(text = movieDetailsResult.title)
                MovieDetailsResult.NoDetails ->  Text(text = movieDetailsResult.toString())
            }

        },
        onFailure = { error ->
            Text(text = error.message ?: "Unknown error")
        }
    )
}
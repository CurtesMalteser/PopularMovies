package com.curtesmalteser.popularmoviesstage1.screen.movieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.nav.Screen
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils
import com.curtesmalteser.popularmoviesstage1.viewmodel.MoviesPresenter

/**
 * Created by António Bastião on 13.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MoviesListScreen(
    navController: NavController,
    presenter: MoviesPresenter,
) {

    val movies by presenter.moviesList.collectAsStateWithLifecycle()

    // TODO: improve with Adaptive or Dynamic count on larger screens
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(
            items = movies.toList(),
            key = { _, item -> item.id }
        ) { _, movie ->

            // TODO: add a placeholder for loading and failure
            GlideImage(
                model = NetworkUtils.getPosterUrl(
                    stringResource(R.string.poster_width_segment),
                    movie.posterPath
                ),
                contentDescription = "My content description",
                modifier = Modifier
                    .clickable(onClick = {
                        navController.navigate(
                            Screen.MovieDetails.createRoute(movie.id),
                        )
                    })
                    .aspectRatio(0.67f),
            )
        }
    }
}

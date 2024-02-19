package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.SrcAtop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils.getPosterUrl


/**
 * Created by António Bastião on 21.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel,
) {

    val movieId : Long = navController.currentBackStackEntry?.arguments
        ?.getLong("movieId") ?: 0

    movieId.takeIf { it > 0 }?.let(viewModel::setupMovieDetailsFor)

    val movieDetails by viewModel.movieDetailsFlow.collectAsStateWithLifecycle(Result.success(MovieDetailsResult.NoDetails))

    movieDetails.fold(
        onSuccess = { movieDetailsResult ->
            when (movieDetailsResult) {
                is MovieDetailsResult.MovieDetailsData -> {
                    BackgroundImage(backdropPath = movieDetailsResult.backdropPath) {
                        Text(
                            text = movieDetailsResult.title,
                            color = Color.White,
                            fontSize = 24.sp,
                        )
                    }
                }
                MovieDetailsResult.NoDetails ->  Text(text = movieDetailsResult.toString())
            }

        },
        onFailure = { error ->
            Text(text = error.message ?: "Unknown error")
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BackgroundImage(
    backdropPath: String,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GlideImage(
            model = getPosterUrl(stringResource(R.string.poster_width_segment), backdropPath),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.9f), SrcAtop),
        )
        content()
    }
}
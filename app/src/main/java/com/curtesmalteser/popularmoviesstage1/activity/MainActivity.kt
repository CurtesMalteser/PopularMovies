package com.curtesmalteser.popularmoviesstage1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.nav.Screen
import com.curtesmalteser.popularmoviesstage1.screen.main.MainScreen
import com.curtesmalteser.popularmoviesstage1.theme.AppTheme
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils
import com.curtesmalteser.popularmoviesstage1.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MoviesListScreen(
    navController: NavController,
    viewModel: MoviesViewModel,
) {

    val movies by viewModel.moviesList.collectAsStateWithLifecycle()

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

@Composable
fun Favorite(navController: NavController) {
    Text(text = "Favorite")
}

@Preview
@Composable
fun ActivityComposablePreview() {
    MainScreen()
}
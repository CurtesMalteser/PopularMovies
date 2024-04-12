package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.SrcAtop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.component.CollapsibleRow
import com.curtesmalteser.popularmoviesstage1.component.details.ReviewsRow
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
        onSuccess = { detailsResult ->
            when (detailsResult) {
                is MovieDetailsResult.MovieDetailsData -> {
                    BackgroundImage(backdropPath = detailsResult.backdropPath) {
                        MovieDetailsHeader(
                            details = { detailsResult },
                        )
                        CollapsibleRow(title = stringResource(id = R.string.string_overview)) {
                            Text(
                                text = detailsResult.overview,
                                color = Color.White,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                       ReviewsRow(reviewsResult = detailsResult.reviewsData)
                    }
                }
                MovieDetailsResult.NoDetails ->  Text(text = detailsResult.toString())
            }

        },
        onFailure = { error ->
            Text(text = error.message ?: "Unknown error")
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsHeader(
    details: () -> MovieDetails,
){
    val mustShowDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(.40f)
            .padding(start = 16.dp, top = 16.dp, bottom = 8.dp, end = 16.dp),
    ) {
        GlideImage(
            model = getPosterUrl(
                stringResource(R.string.poster_width_segment),
                details().posterPath
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth(.40f)
                .padding(end = 8.dp),
        )
        Column(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = details().title,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(painter = painterResource(
                    id = R.drawable.ic_star_red_24dp),
                    contentDescription = "Vote Average red star icon",
                    modifier = Modifier.padding(end = 8.dp),
                )
                Text(
                    text = stringResource(id = R.string.string_vote_average, details().voteAverage),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
            Text(
                text = "${stringResource(id = R.string.release_date_string)} ${details().releaseDate}",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row (
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { mustShowDialog.value = true },
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_heart_white),
                        contentDescription = "Add to favorites icon",
                    )
                }

                Text(
                    text = stringResource(id = R.string.string_add_fav),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }

            mustShowDialog.value.takeIf { it }?.let {
                Dialog(onDismissRequest = { mustShowDialog.value = false }) {
                    Text(
                        text = "Feature will be implemented soon",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BackgroundImage(
    backdropPath: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // TODO: add a placeholder for loading and failure
        GlideImage(
            model = getPosterUrl(stringResource(R.string.poster_width_segment), backdropPath),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.9f), SrcAtop),
        )
        Column (modifier = Modifier.matchParentSize()){ content() }
    }
}

@Composable
@Preview
fun HeaderPreview() {
    MovieDetailsHeader(details = {
        object : MovieDetails{
            override val id = 1L
            override val title = "Title"
            override val voteAverage = "7.5"
            override val posterPath = "poster"
            override val backdropPath = "backdrop"
            override val overview = "overview"
            override val releaseDate = "releaseDate"
        }
    })
}
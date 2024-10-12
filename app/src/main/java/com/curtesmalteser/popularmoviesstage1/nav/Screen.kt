package com.curtesmalteser.popularmoviesstage1.nav

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.curtesmalteser.popularmoviesstage1.R

/**
 * Created by António Bastião on 17.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
sealed class Screen(
    val route: String,
    @StringRes val stringId: Int,
    val icon: @Composable () -> Unit,
) {
    data object Popular : Screen(
        route = "popular",
        stringId = R.string.string_popular,
        icon = {
            Icon(
                painterResource(id = R.drawable.ic_thumb_up_white_24dp),
                contentDescription = null
            )
        }
    )

    data object TopRated : Screen(
        route = "toprated",
        stringId = R.string.string_top_rated,
        icon = {
            Icon(
                painterResource(id = R.drawable.ic_top_games_star_white),
                contentDescription = null
            )
        }
    )

    data object Favorite : Screen(
        route = "favorite",
        stringId = R.string.string_favorite,
        icon = {
            Icon(
                painterResource(id = R.drawable.ic_heart_white),
                contentDescription = null
            )
        }
    )

    data object MovieDetails : Screen(
        route = "movieDetails/{movieId}",
        stringId = R.string.string_extra,
        icon = { Icon(imageVector = Icons.Filled.AccountBox, contentDescription = null) }
    ) {
        fun createRoute(movieId: Long) = "movieDetails/$movieId"
    }
}
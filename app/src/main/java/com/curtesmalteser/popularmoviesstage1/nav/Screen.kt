package com.curtesmalteser.popularmoviesstage1.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.curtesmalteser.popularmoviesstage1.R

/**
 * Created by António Bastião on 17.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
sealed class Screen(
    val route: String,
    @StringRes val stringId: Int,
    @DrawableRes val drawableId: Int
) {
    data object Popular : Screen(
        route = "popular",
        stringId = R.string.string_popular,
        drawableId = R.drawable.ic_thumb_up_white_24dp
    )

    data object TopRated : Screen(
        route = "toprated",
        stringId = R.string.string_top_rated,
        drawableId = R.drawable.ic_top_games_star_white
    )

    data object Favorite : Screen(
        route = "favorite",
        stringId = R.string.string_favorite,
        drawableId = R.drawable.ic_heart_white
    )
}
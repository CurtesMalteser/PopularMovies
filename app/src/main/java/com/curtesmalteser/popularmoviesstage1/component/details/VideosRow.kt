package com.curtesmalteser.popularmoviesstage1.component.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.curtesmalteser.popularmovies.data.VideosModelData
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.component.CollapsibleRow
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils.getThumbnail

/**
 * Created by António Bastião on 12.04.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideosRow(videosResult: Result<VideosModelData>) {
    CollapsibleRow(title = stringResource(id = R.string.string_videos)) {
        videosResult.fold(
            onSuccess = { reviews ->
                Column {
                    reviews.videosModels.map {
                        DetailsCard {
                            Row {
                                GlideImage(
                                    model = getThumbnail(it.key),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier
                                        .fillMaxWidth(.40f)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                )

                                Text(
                                    text = it.name,
                                    color = Color.White,
                                    modifier = Modifier.padding(8.dp),
                                )

                            }
                        }
                    }
                }

            },
            onFailure = { error ->
                Text(
                    text = error.message ?: "Unknown error",
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(16.dp),
                )
            }
        )
    }
}
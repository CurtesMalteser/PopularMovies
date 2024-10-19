package com.curtesmalteser.popularmoviesstage1.component.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.component.CollapsibleRow

/**
 * Created by António Bastião on 12.04.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun ReviewsRow(reviewsResult: Result<ReviewsModelData>) {
    CollapsibleRow(title = stringResource(id = R.string.string_reviews)) {
        reviewsResult.fold(
            onSuccess = { reviews ->

                Column {
                    reviews.reviewsModels.map {
                        DetailsCard {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Text(
                                        text = "Author: ",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = it.author,
                                        color = Color.White,
                                    )
                                }
                                Text(
                                    text = it.content,
                                    color = Color.White,
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
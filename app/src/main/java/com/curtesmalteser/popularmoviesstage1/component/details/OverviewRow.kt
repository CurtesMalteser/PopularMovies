package com.curtesmalteser.popularmoviesstage1.component.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.component.CollapsibleRow

/**
 * Created by António Bastião on 12.04.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun OverviewRow(overview: String) {
    CollapsibleRow(title = stringResource(id = R.string.string_overview)) {
        Text(
            text = overview,
            color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp),
        )
    }
}
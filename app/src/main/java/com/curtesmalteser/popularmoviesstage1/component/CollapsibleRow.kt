package com.curtesmalteser.popularmoviesstage1.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by António Bastião on 12.04.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun CollapsibleRow(
    title: String,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {

    val isExpanded = rememberSaveable(key = title) { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded.value = !isExpanded.value }
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red)
                    .padding(16.dp)

            )
        }

        AnimatedVisibility(visible = isExpanded.value, content = content)

    }
}
package com.curtesmalteser.popularmoviesstage1.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    // todo: replace with state from viewModel (to be implemented)
    val isExpanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .background(color = Color.Red)
                .clickable { isExpanded.value = !isExpanded.value }
        )

        AnimatedVisibility(visible = isExpanded.value, content = content)

    }
}
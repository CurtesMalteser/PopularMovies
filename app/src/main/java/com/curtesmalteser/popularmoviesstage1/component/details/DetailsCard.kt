package com.curtesmalteser.popularmoviesstage1.component.details

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by António Bastião on 19.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun DetailsCard(
    content: @Composable ColumnScope.() -> Unit
) = Card(
    modifier = Modifier.padding(8.dp).fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.3f)),
    content = content,
)
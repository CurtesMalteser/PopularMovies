package com.curtesmalteser.popularmoviesstage1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.curtesmalteser.popularmoviesstage1.R

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityContent()
        }
    }
}

@Composable
fun ActivityContent() {

    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        stringResource(R.string.string_popular) to painterResource(R.drawable.ic_thumb_up_white_24dp),
        stringResource(R.string.string_top_rated) to painterResource(R.drawable.ic_top_games_star_white),
        stringResource(R.string.string_favorite) to painterResource(R.drawable.ic_heart_white),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(item.second, contentDescription = null) },
                        label = { Text(item.first) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        selectedContentColor = colorResource(id = R.color.colorAccent),
                        unselectedContentColor = colorResource(id = R.color.white)
                    )
                }
            }
        }
    ) {
        // Screen content
        Text(text = "Hello World!", modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
fun ActivityComposablePreview() {
    ActivityContent()
}
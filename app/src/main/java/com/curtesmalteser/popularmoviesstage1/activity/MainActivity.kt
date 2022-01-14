package com.curtesmalteser.popularmoviesstage1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
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

    var selectedItem by remember { mutableStateOf(0) }
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
    }
}

@Preview
@Composable
fun ActivityComposablePreview() {
    ActivityContent()
}
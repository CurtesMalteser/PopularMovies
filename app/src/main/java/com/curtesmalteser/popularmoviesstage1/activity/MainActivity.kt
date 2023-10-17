package com.curtesmalteser.popularmoviesstage1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.nav.Screen
import com.curtesmalteser.popularmoviesstage1.viewmodel.MoviesViewModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.PopularMoviesViewModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    val navController = rememberNavController()

    val items = listOf(
        Screen.Popular,
        Screen.TopRated,
        Screen.Favorite,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = screen.drawableId),
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(id = screen.stringId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        selectedContentColor = colorResource(id = R.color.colorAccent),
                        unselectedContentColor = colorResource(id = R.color.white)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Popular.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Popular.route) {
                val viewModel = hiltViewModel<PopularMoviesViewModel>()
                MoviesListScreen(navController, viewModel)
            }
            composable(Screen.TopRated.route) {
                val viewModel = hiltViewModel<TopRatedMoviesViewModel>()
                MoviesListScreen(navController, viewModel)
            }
            composable(Screen.Favorite.route) { Favorite(navController) }
        }
    }
}

@Composable
fun MoviesListScreen(
    navController: NavController,
    viewModel: MoviesViewModel,
) {

    val movies = viewModel.moviesList.collectAsStateWithLifecycle()

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(
            items = movies.value.toList(),
            key = { _, item -> item.id }
        ) { _, movie ->
            Text(movie.title)
        }
    }
}

@Composable
fun Favorite(navController: NavController) {
    Text(text = "Favorite")
}

@Preview
@Composable
fun ActivityComposablePreview() {
    ActivityContent()
}
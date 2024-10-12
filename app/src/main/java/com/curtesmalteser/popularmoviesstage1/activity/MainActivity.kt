package com.curtesmalteser.popularmoviesstage1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.nav.Screen
import com.curtesmalteser.popularmoviesstage1.screen.details.MovieDetailsScreen
import com.curtesmalteser.popularmoviesstage1.screen.details.MovieDetailsViewModel
import com.curtesmalteser.popularmoviesstage1.theme.AppTheme
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils
import com.curtesmalteser.popularmoviesstage1.viewmodel.MoviesViewModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.PopularMoviesViewModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ActivityContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        bottomBar = {
            if (shouldShowBottomBar(navController)) {
                MainBottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = "main",
            Modifier.padding(innerPadding)
        ) {
            navigation(startDestination = Screen.Popular.route, route = "main") {
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

            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(navArgument("movieId") { type = NavType.LongType })
            ) {
                val viewModel = hiltViewModel<MovieDetailsViewModel>()
                MovieDetailsScreen(navController, viewModel)
            }
        }
    }
}

@Composable
fun MainBottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Popular,
        Screen.TopRated,
        Screen.Favorite,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectedColor = colorResource(id = R.color.colorAccent)
    val unselectedColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = screen.icon,
                label = { Text(stringResource(id = screen.stringId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        val destinationIdToPop = currentDestination?.id ?: navController.graph.findStartDestination().id
                        popUpTo(destinationIdToPop) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = selectedColor,
                    selectedTextColor = selectedColor,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor,
                    disabledIconColor = colorResource(id = R.color.colorPrimaryDark).copy(alpha = 0.38f),
                    disabledTextColor = unselectedColor.copy(alpha = 0.38f),
                )
            )
        }
    }
}

@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    return currentDestination in listOf(Screen.Popular.route, Screen.TopRated.route, Screen.Favorite.route)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MoviesListScreen(
    navController: NavController,
    viewModel: MoviesViewModel,
) {

    val movies by viewModel.moviesList.collectAsStateWithLifecycle()

    // TODO: improve with Adaptive or Dynamic count on larger screens
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(
            items = movies.toList(),
            key = { _, item -> item.id }
        ) { _, movie ->

            // TODO: add a placeholder for loading and failure
            GlideImage(
                model = NetworkUtils.getPosterUrl(
                    stringResource(R.string.poster_width_segment),
                    movie.posterPath
                ),
                contentDescription = "My content description",
                modifier = Modifier
                    .clickable(onClick = {
                        navController.navigate(
                            Screen.MovieDetails.createRoute(movie.id),
                        )
                    })
                    .aspectRatio(0.67f),
            )
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
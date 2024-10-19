package com.curtesmalteser.popularmoviesstage1.screen.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.curtesmalteser.popularmoviesstage1.nav.Screen

/**
 * Created by António Bastião on 13.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Composable
fun MainBottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Popular,
        Screen.TopRated,
        Screen.Favorite,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectedColor = Color.Red
    val unselectedColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItemContent(
                screen,
                currentDestination,
                navController,
                selectedColor,
                unselectedColor,
            )
        }
    }
}

@Composable
private fun RowScope.NavigationBarItemContent(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavController,
    selectedColor: Color,
    unselectedColor: Color
) {
    NavigationBarItem(
        icon = screen.icon,
        label = { Text(stringResource(id = screen.stringId)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigateToScreen(screen, currentDestination)
        },
        colors = navigationBarItemColors(selectedColor, unselectedColor)
    )
}


private fun NavController.navigateToScreen(
    screen: Screen,
    currentDestination: NavDestination?
) {
    navigate(screen.route) {
        val destinationIdToPop =
            currentDestination?.id ?: graph.findStartDestination().id
        popUpTo(destinationIdToPop) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigationBarItemColors(
    selectedColor: Color,
    unselectedColor: Color
) = NavigationBarItemColors(
    selectedIconColor = selectedColor,
    selectedTextColor = selectedColor,
    selectedIndicatorColor = Color.Transparent,
    unselectedIconColor = unselectedColor,
    unselectedTextColor = unselectedColor,
    disabledIconColor = unselectedColor.copy(alpha = 0.38f),
    disabledTextColor = unselectedColor.copy(alpha = 0.38f),
)

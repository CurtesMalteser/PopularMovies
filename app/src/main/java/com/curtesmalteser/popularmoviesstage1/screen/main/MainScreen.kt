package com.curtesmalteser.popularmoviesstage1.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.curtesmalteser.popularmoviesstage1.R

/**
 * Created by António Bastião on 13.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        bottomBar = {
            navController.takeIf { it.shouldShowBottomBar() }
                ?.let { MainBottomNavigationBar(it) }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = "main",
            Modifier.padding(innerPadding)
        ) {
            mainNavigation(navController)
            movieDetailsNavigation(navController)
        }
    }
}


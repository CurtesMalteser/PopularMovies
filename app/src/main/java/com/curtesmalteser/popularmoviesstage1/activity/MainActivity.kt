package com.curtesmalteser.popularmoviesstage1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.curtesmalteser.popularmoviesstage1.screen.main.MainScreen
import com.curtesmalteser.popularmoviesstage1.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

@Preview
@Composable
fun ActivityComposablePreview() {
    MainScreen()
}
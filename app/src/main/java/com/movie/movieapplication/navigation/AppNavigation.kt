package com.movie.movieapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movie.movieapplication.screens.mainscreen.MainScreen
import com.movie.movieapplication.screens.splashscreen.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AllScreens.SplashScreen.name) {
        composable(AllScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(AllScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }
    }
}
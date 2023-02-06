package com.movie.movieapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.movie.movieapplication.screens.mainscreen.MainScreen
import com.movie.movieapplication.screens.moviedetailscreen.MovieDetailScreen
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

        val route = AllScreens.MovieDetailScreen.name
        composable("$route/{movieName}", arguments = listOf(
            navArgument(name = "movieName") {
                type = NavType.StringType
            }
        )) {
            it.arguments?.getString("movieName").let {
                MovieDetailScreen(navController = navController, movieName = it.toString())
            }
        }
    }
}
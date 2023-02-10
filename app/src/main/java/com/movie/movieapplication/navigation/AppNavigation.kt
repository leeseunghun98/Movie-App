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
import com.movie.movieapplication.data.NavBoxData
import com.movie.movieapplication.model.BoxOfficeInfo
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
        composable("$route/{movieBoxData}", arguments = listOf(
            navArgument(name = "movieBoxData") {
                type = NavBoxData()
            }
        )) {
            it.arguments?.let {
                val boxData = it.getParcelable<BoxOfficeInfo>("movieBoxData")
                MovieDetailScreen(navController = navController, movieBoxData = boxData!!)
            }
        }
    }
}
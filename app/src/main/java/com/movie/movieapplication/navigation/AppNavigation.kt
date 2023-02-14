package com.movie.movieapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.movie.movieapplication.data.NavBoxData
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.screens.actor.ActorDetailScreen
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

        val routeMovieDetailScreen = AllScreens.MovieDetailScreen.name
        composable("$routeMovieDetailScreen/{movieName}/{movieCode}/{movieBoxData}", arguments = listOf(
            navArgument(name = "movieName") {
                type = NavType.StringType
            },
            navArgument(name = "movieCode") {
                type = NavType.StringType
            },
            navArgument(name = "movieBoxData") {
                type = NavBoxData()
            }
        )) {
            it.arguments?.let {
                val movieName = it.getString("movieName")
                val movieCode = it.getString("movieCode")
                val boxData = it.getParcelable<BoxOfficeInfo>("movieBoxData")
                MovieDetailScreen(navController = navController, movieBoxData = boxData, movieName = movieName!!, movieCode = movieCode!!)
            }
        }

        val routeActorDetailScreen = AllScreens.ActorDetailScreen.name
        composable("$routeActorDetailScreen/{actorName}/{filmoName}") {
            it.arguments?.let { it ->
                val actorName = it.getString("actorName")
                val filmoName = it.getString("filmoName")
                ActorDetailScreen(navController = navController, actorName = actorName!!, filmoName = filmoName!!)
            }
        }
    }
}
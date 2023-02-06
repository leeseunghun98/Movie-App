package com.movie.movieapplication.screens.moviedetailscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.screens.MovieViewModel
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.widgets.CenterCircularProgressIndicator
import com.movie.movieapplication.widgets.MovieAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScreen(navController: NavController, movieName: String) {
    BasicScreen {
        MovieDetailScaffold(navController = navController, movieName = movieName)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScaffold(
    navController: NavController,
    movieViewModel: MovieViewModel = hiltViewModel(),
    movieName: String
) {
    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController)
        }) {

        val movie =
            produceState(initialValue = DataOrException<JsonObject, Boolean, Exception>(loading = true)) {
                value = movieViewModel.getMovieInfo(movieName)
            }.value
        if (movie.loading == true) {
            CenterCircularProgressIndicator()
        } else if (movie.exception != null) {
            Log.d("로그", "MovieDetailScreen: ${movie.exception}")
            navController.popBackStack()
        } else if (movie.data != null) {
            MovieDetailContent(movieData = movie.data!!)
        }
    }
}

@Composable
fun MovieDetailContent(movieData: JsonObject) {
    Text(text = movieData.toString())
}
package com.movie.movieapplication.screens.mainscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.boxOfficeList
import com.movie.movieapplication.screens.MovieViewModel
import com.movie.movieapplication.ui.theme.ContentBackgroundColor
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.widgets.MovieAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel(), navController: NavController) {

    val mode = mainViewModel.mode.collectAsState().value
    val boxOfficeData = mainViewModel.movieList.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize(), color = MainColor) {
        if (boxOfficeData.loading == true) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.size(200.dp))
            }
        } else if (boxOfficeData.data != null) {
            val boxDataList = boxOfficeData.data!!.boxOfficeList
            MainScaffold(
                boxOfficeList = boxDataList,
                navController = navController
            ) {
                mainViewModel.updateBoxOffice(it)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    boxOfficeList: List<BoxOfficeInfo>,
    navController: NavController,
    movieViewModel: MovieViewModel = hiltViewModel(),
    onModeChanged: (String) -> Unit
) {
    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController, isMainScreen = true)
        }) {
//        MainContent()
//        val testString = URLEncoder.encode("교섭", "UTF-8")
        val testString = "교섭"
        val testText =
            produceState(initialValue = DataOrException<JsonObject, Boolean, Exception>(loading = true)) {
                value = movieViewModel.getMovieInfo(testString)
            }.value
        Log.d("로그", "testString: $testString")
        if (testText.loading == true) {
            Log.d("로그", "Loading...")
            CircularProgressIndicator()
        } else if (testText.exception != null) {
            Log.d("로그", "testText.exception: ${testText.exception}")
        } else if (testText.data != null) {
            Log.d("로그", "Success!: ${testText.data}");
            Column() {
                Text(text = testText.data.toString())
                Text(text = testText.loading.toString())
                Text(text = testText.exception.toString())
            }
        }
    }
}

@Composable
fun MainContent() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        color = DeepMainColor,
        shape = RoundedCornerShape(
            CornerSize(12.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = ContentBackgroundColor)
            ) {
                MovieCard()
                MovieCard()
                MovieCard()
            }
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = ContentBackgroundColor)
            ) {
                MovieCard()
                MovieCard()
                MovieCard()
            }
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = ContentBackgroundColor)
            ) {
                MovieCard()
                MovieCard()
                MovieCard()
                MovieCard()
            }
        }
    }
}

@Composable
fun MovieCard() {
    Surface(
        modifier = Modifier
            .size(100.dp)
            .padding(12.dp), color = Color.LightGray
    ) {
        Box {

        }

    }
}

@Composable
@Preview
fun MovieCardPreview() {
    MovieCard()
}

package com.movie.movieapplication.screens.mainscreen

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.boxOfficeList
import com.movie.movieapplication.ui.theme.ContentBackgroundColor
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.widgets.CenterCircularProgressIndicator
import com.movie.movieapplication.widgets.MovieAppBar
import com.movie.movieapplication.widgets.RankMovieCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {
    BasicScreen {
        MainScaffold(
            navController = navController
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val mode = mainViewModel.mode.collectAsState().value
    val boxOfficeData = mainViewModel.movieList.collectAsState().value

    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController, isMainScreen = true)
        }) {
        if (boxOfficeData.loading == true) {
            CenterCircularProgressIndicator()
        } else if (boxOfficeData.data != null) {
            val boxDataList = boxOfficeData.data!!.boxOfficeList
            MainContent(boxOfficeList = boxDataList, navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(
    boxOfficeList: List<BoxOfficeInfo>,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
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
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            val mode = mainViewModel.mode.collectAsState().value
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                text = mode,
                color = Color.White,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            val modifier: Modifier = Modifier.weight(1f)
            val rowModifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = ContentBackgroundColor,
                    shape = RoundedCornerShape(CornerSize(15.dp))
                )
            Row(modifier = rowModifier) {
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 1, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 0, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 2, navController = navController)
            }
            Divider()
            Row(modifier = rowModifier) {
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 3, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 4, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 5, navController = navController)
            }
            Divider()
            Row(modifier = rowModifier) {
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 6, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 7, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 8, navController = navController)
                RankMovieCard(modifier = modifier, boxOfficeList = boxOfficeList, rank = 9, navController = navController)
            }
        }
    }
}


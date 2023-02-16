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
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.boxOfficeList
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
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
    val mode = mainViewModel.mode.collectAsState().value
    val rowModifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        .background(
            color = ContentBackgroundColor,
            shape = RoundedCornerShape(CornerSize(15.dp))
        )
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
            Row(modifier = rowModifier) {
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[1], rank = 1, navController = navController, movieCode = boxOfficeList[1].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[0], rank = 0, navController = navController, movieCode = boxOfficeList[0].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[2], rank = 2, navController = navController, movieCode = boxOfficeList[2].movieCd)
            }
            Divider()
            Row(modifier = rowModifier) {
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[3], rank = 3, navController = navController, movieCode = boxOfficeList[3].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[4], rank = 4, navController = navController, movieCode = boxOfficeList[4].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[5], rank = 5, navController = navController, movieCode = boxOfficeList[5].movieCd)
            }
            Divider()
            Row(modifier = rowModifier) {
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[6], rank = 6, navController = navController, movieCode = boxOfficeList[6].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[7], rank = 7, navController = navController, movieCode = boxOfficeList[7].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[8], rank = 8, navController = navController, movieCode = boxOfficeList[8].movieCd)
                RankMovieCard(modifier = modifier, movieBoxInfo = boxOfficeList[9], rank = 9, navController = navController, movieCode = boxOfficeList[9].movieCd)
            }
        }
    }
}
//
//@Composable
//fun RankMovieCardWithLoadingException(
//    modifier: Modifier = Modifier,
//    movieBoxInfo: BoxOfficeInfo,
//    navController: NavController,
//    rank: Int,
//    movieCode: String,
//    searchMovieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel()
//) {
//    val movieInfo = produceState(initialValue = DataOrException<SearchMovieInfo, Boolean, Exception>(loading = true)) {
//        value = searchMovieInfoViewModel.getSearchMovieInfo(movieCode)
//    }.value
//    if (movieInfo.loading == true) {
//        CenterCircularProgressIndicator()
//    } else if (movieInfo.exception != null) {
//        Log.d("로그", "MainScreen : Error while fetching data! ${movieInfo.exception}")
//    } else if (movieInfo.data != null) {
//        RankMovieCard(
//            modifier = modifier,
//            movieBoxInfo = movieBoxInfo,
//            rank = rank,
//            navController = navController,
//            movieInfo = movieInfo.data!!.movieInfoResult.movieInfo
//        )
//    }
//}
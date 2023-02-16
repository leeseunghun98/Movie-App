package com.movie.movieapplication.screens.moviedetailscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.searchmovieinfo.*
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.utils.*
import com.movie.movieapplication.widgets.*
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    movieBoxData: BoxOfficeInfo?,
    movieCode: String
) {
    BasicScreen {
        MovieDetailScaffold(navController = navController, movieBoxData = movieBoxData, movieCode = movieCode)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScaffold(
    navController: NavController,
    movieBoxData: BoxOfficeInfo?,
    movieCode: String,
    movieViewModel: MovieViewModel = hiltViewModel(),
    searchMovieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel()
) {
    val movieInfoData = produceState(initialValue = DataOrException<SearchMovieInfo, Boolean, Exception>(loading = true)) {
        value = searchMovieInfoViewModel.getSearchMovieInfo(movieCd = movieCode)
    }.value

    if (movieInfoData.loading == true) {
        CenterCircularProgressIndicator()
    } else if (movieInfoData.exception != null) {
        Log.d("로그", "MovieDetailScreen : Error while fetching data! ${movieInfoData.exception}")
    } else if (movieInfoData.data != null) {
        val movieName = movieInfoData.data!!.movieInfoResult.movieInfo.movieNm
        Scaffold(modifier = Modifier,
            backgroundColor = MainColor,
            topBar = {
                MovieAppBar(navController = navController, title = movieName)
            }) {
            MovieDetailContent(
                movieBoxData = movieBoxData,
                navController = navController,
                movieInfo = movieInfoData.data!!.movieInfoResult.movieInfo
            )
        }
    }
}

@Composable
fun MovieDetailContent(
    movieBoxData: BoxOfficeInfo?,
    movieInfo: MovieInfo,
    searchMovieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel(),
    navController: NavController
) {
    val movieInfoData = produceState(initialValue = DataOrException<SearchMovieInfo, Boolean, Exception>(loading = true)) {
        value = searchMovieInfoViewModel.getSearchMovieInfo(movieCd = movieInfo.movieCd)
    }.value
    Column(modifier = Modifier.fillMaxSize()) {
        if (movieInfoData.loading == true) {
            CenterCircularProgressIndicator()
        } else if (movieInfoData.exception != null) {
            Log.d("로그", "MovieDetailScreen : Error while fetching data!!(SearchMovieInfoApi) ${movieInfoData.exception}")
        } else if (movieInfoData.data != null) {
            val movieInfo = movieInfoData.data!!.movieInfoResult.movieInfo
            MovieInformationCard(movieBoxData = movieBoxData, movieInfo = movieInfo)

            Divider()

            Actors(actorList = getActors(movieInfoData.data!!), movieName = movieInfo.movieNm, navController = navController)
        }
        if (movieBoxData != null){
            MovieBoxDataCard(movieBoxData)
        }
    }
}

@Composable
fun MovieInformationCard(movieBoxData: BoxOfficeInfo?, movieInfo: MovieInfo) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(230.dp),
        shape = RoundedCornerShape(
            CornerSize(15.dp)
        ),
        backgroundColor = DeepMainColor
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(0.4f), verticalArrangement = Arrangement.Center) {
                RankMovieCard(
                    modifier = Modifier.fillMaxWidth(),
                    movieBoxInfo = movieBoxData,
                    navController = null,
                    movieInfo = movieInfo
                )
            }

            Surface(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(4.dp),
                color = DeepMainColor
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())) {
                        val textstyle = TextStyle(
                            color = Color.Gray,
                            fontSize = 20.sp
                        )
                        Text(text = "감독 : ${getDirector(movieInfo.directors).joinToString(", ")}", style = textstyle)
                        Text(text = "개봉연도 : ${movieInfo.prdtYear}", style = textstyle)
                        Text(text = "장르 : ${getGenre(movieInfo.genres).joinToString(", ")}", style = textstyle)
                        Text(text = "제작국가 : ${getNations(movieInfo.nations).joinToString(", ")}", style = textstyle)
                        Text(text = "관람등급 : ${getGradeNm(movieInfo.audits).joinToString(", ")}", style = textstyle)
                    }
                }
            }

        }
    }
}



@Composable
fun MovieBoxDataCard(movieBoxData: BoxOfficeInfo) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(
            CornerSize(15.dp)
        ),
        color = DeepMainColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = getIntenAnnotatedString(
                    string = "순위",
                    num = movieBoxData.rank.toInt(),
                    inten = movieBoxData.rankInten.toInt()
                )
            )
            Text(
                text = "누적매출액 : ${
                    formatNumber(
                        num = movieBoxData.salesAcc.toLong(),
                        isMoney = true
                    )
                }", color = Color.LightGray
            )
            Text(
                text = getIntenAnnotatedString(
                    string = "당일 관객 수",
                    num = movieBoxData.audiCnt.toInt(),
                    inten = movieBoxData.audiInten.toInt()
                )
            )
            Text(
                text = "누적 관객 수 : ${formatNumber(num = movieBoxData.audiAcc.toLong())}",
                color = Color.LightGray
            )
            Text(text = "당일 상영된 횟수 : ${movieBoxData.showCnt}", color = Color.LightGray)
        }
    }
}

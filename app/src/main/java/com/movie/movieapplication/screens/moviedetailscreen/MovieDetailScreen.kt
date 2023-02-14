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
import com.google.gson.JsonObject
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.utils.*
import com.movie.movieapplication.widgets.*
import kotlinx.coroutines.*
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    movieBoxData: BoxOfficeInfo?,
    movieName: String,
    movieCode: String
) {
    BasicScreen {
        MovieDetailScaffold(navController = navController, movieBoxData = movieBoxData, movieName = movieName, movieCode = movieCode)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScaffold(
    navController: NavController,
    movieBoxData: BoxOfficeInfo?,
    movieName: String,
    movieCode: String,
    movieViewModel: MovieViewModel = hiltViewModel(),
) {
    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController, title = movieName)
        }) {

        val movieDetailData =
            produceState(initialValue = DataOrException<JsonObject, Boolean, Exception>(loading = true)) {
                value = movieViewModel.getMovieInfo(movieName)
            }.value
        Log.d("로그", "1  ${movieDetailData.data}")
        if (movieDetailData.loading == true) {
            CenterCircularProgressIndicator()
        } else if (movieDetailData.exception != null) {
            CenterCircularProgressIndicator()
            // TODO : HTTP 429 ERROR
//            movieDetailData.exception = null
//            GlobalScope.launch(Dispatchers.IO) {
//                do {
//                    delay(500)
//                    movieDetailData = movieViewModel.getMovieInfo(movieName)
//                    delay(500)
//                } while (movieDetailData.data == null)
//                state.value = !state.value
//            }
        } else if (movieDetailData.data != null) {
            MovieDetailContent(
                movieDetailInfo = getMovieItemsFromMovieInfo(movieDetailData),
                movieBoxData = movieBoxData,
                movieCode = movieCode,
                navController = navController,
                movieName = movieName
            )
        }
    }
}

@Composable
fun MovieDetailContent(
    movieDetailInfo: JSONObject,
    movieBoxData: BoxOfficeInfo?,
    movieCode: String,
    movieName: String,
    searchMovieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel(),
    navController: NavController
) {
    val movieInfo = produceState(initialValue = DataOrException<SearchMovieInfo, Boolean, Exception>(loading = true)) {
        value = searchMovieInfoViewModel.getSearchMovieInfo(movieCode)
    }.value
    Column(modifier = Modifier.fillMaxSize()) {

        MovieInformationCard(movieBoxData = movieBoxData, movieDetailInfo = movieDetailInfo, movieName = movieName, movieCode = movieCode)

        Divider()

        if (movieInfo.loading == true) {
            CenterCircularProgressIndicator()
        } else if (movieInfo.exception != null) {
            Log.d("로그", "MovieDetailScreen : Error while fetching data!!(SearchMovieInfoApi) ${movieInfo.exception}")
        } else if (movieInfo.data != null) {
            Actors(actorList = getActors(movieInfo.data!!), movieName = movieName, navController = navController)
        }
        if (movieBoxData != null){
            MovieBoxDataCard(movieBoxData)
        }
    }
}

@Composable
fun MovieInformationCard(movieBoxData: BoxOfficeInfo?, movieDetailInfo: JSONObject, movieCode: String, movieName: String) {
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
                    movieInfo = movieBoxData,
                    navController = null,
                    movieCode = movieCode,
                    movieName = movieName
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
                        Text(text = "Subtitle : ${movieDetailInfo.get("subtitle") ?: ""}",style = textstyle)
                        val directorList = getPeople((movieDetailInfo.get("director") ?: "").toString())
                        Text(text = "감독 : ${directorList.joinToString(", ")}", style = textstyle)
                        Text(text = "제작 년도 : ${movieDetailInfo.get("pubDate") ?: ""}", style = textstyle)
                        Text(text = "평점 : ${movieDetailInfo.get("userRating") ?: ""}", style = textstyle)
                    }

                    Surface(modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clickable {
                            uriHandler.openUri(
                                movieDetailInfo
                                    .get("link")
                                    .toString()
                            )
                        }, color = Color(0xFFC0C0C0),
                        shape = RoundedCornerShape(CornerSize(15.dp))
                    ) {
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()) {
                            Text(text = "영화정보 보러가기", fontSize = 15.sp, fontWeight = FontWeight.Light, modifier = Modifier)
                        }
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

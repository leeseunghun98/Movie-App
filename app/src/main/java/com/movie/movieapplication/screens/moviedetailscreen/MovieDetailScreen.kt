package com.movie.movieapplication.screens.moviedetailscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.searchmovieinfo.Actor
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.utils.getActors
import com.movie.movieapplication.utils.getMovieItemsFromMovieInfo
import com.movie.movieapplication.utils.getPeople
import com.movie.movieapplication.widgets.CenterCircularProgressIndicator
import com.movie.movieapplication.widgets.MovieAppBar
import com.movie.movieapplication.widgets.RankMovieCard
import kotlinx.coroutines.*
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScreen(navController: NavController, movieBoxData: BoxOfficeInfo) {
    BasicScreen {
        MovieDetailScaffold(navController = navController, movieBoxData = movieBoxData)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScaffold(
    navController: NavController,
    movieBoxData: BoxOfficeInfo,
    movieViewModel: MovieViewModel = hiltViewModel(),
) {
    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController, title = movieBoxData.movieNm)
        }) {

        val movieName = movieBoxData.movieNm
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
                movieBoxData = movieBoxData
            )
        }
    }
}

@Composable
fun MovieDetailContent(movieDetailInfo: JSONObject, movieBoxData: BoxOfficeInfo, searchMovieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel()) {
    val movieInfo = produceState(initialValue = DataOrException<SearchMovieInfo, Boolean, Exception>(loading = true)) {
        value = searchMovieInfoViewModel.getSearchMovieInfo(movieBoxData.movieCd)
    }.value
    Column(modifier = Modifier.fillMaxSize()) {

        MovieInformationCard(movieBoxData = movieBoxData, movieDetailInfo = movieDetailInfo)

        Divider()

        if (movieInfo.loading == true) {
            CenterCircularProgressIndicator()
        } else if (movieInfo.exception != null) {
            Log.d("로그", "MovieDetailScreen : Error while fetching data!!(SearchMovieInfoApi) ${movieInfo.exception}")
        } else if (movieInfo.data != null) {
            Actors(actorList = getActors(movieInfo.data!!))
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = movieBoxData.toString())
            Text(text = "개봉일 : ${movieBoxData.openDt}")
            Text(text = "순위 : ${movieBoxData.rank} / ${movieBoxData.rankInten}") // 증감분 포함
            Text(text = "누적매출액 : ${movieBoxData.salesAcc}")
            Text(text = "당일 관객 수 : ${movieBoxData.audiCnt} / ${movieBoxData.audiInten}") // 전일대비도 audiointen 포함
            Text(text = "누적 관객 수 : ${movieBoxData.audiAcc}")
            Text(text = "당일 상영된 횟수 : ${movieBoxData.showCnt}")
        }
    }
}

@Composable
fun Actors(actorList: List<Actor>) {
    // TODO : Fetch

    Surface(modifier = Modifier.padding(4.dp), color = DeepMainColor, shape = RoundedCornerShape(
        CornerSize(15.dp)
    )) {
        LazyRow(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(2.dp)) {
            items(items = actorList) { item ->
                ActorCard(actorInfo = item)
            }
        }
    }
}

@Composable
fun ActorCard(actorInfo: Actor) {
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .width(100.dp)
            .height(70.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        elevation = 4.dp,
        color = Color(0xFF8C8D85)
    ) {
        Log.d("로그", "${actorInfo}")
    }
}

@Composable
fun MovieInformationCard(movieBoxData: BoxOfficeInfo, movieDetailInfo: JSONObject) {
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
                    navController = null
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
                        if (movieBoxData.rankOldAndNew == "NEW") {
                            Text(
                                text = "NEW!",
                                style = TextStyle(
                                    color = Color.Yellow,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
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
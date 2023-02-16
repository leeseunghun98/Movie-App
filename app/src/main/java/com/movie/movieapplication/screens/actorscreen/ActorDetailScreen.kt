package com.movie.movieapplication.screens.actorscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.actor.ActorInfo
import com.movie.movieapplication.model.actor.PeopleInfo
import com.movie.movieapplication.model.actorcode.ActorCode
import com.movie.movieapplication.screens.viewmodels.ActorInfoViewModel
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.widgets.CenterCircularProgressIndicator
import com.movie.movieapplication.widgets.MovieAppBar
import com.movie.movieapplication.widgets.MovieCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActorDetailScreen(navController: NavController, actorName: String, filmoName: String) {
    BasicScreen {
        ActorDetailScaffold(navController = navController, actorName = actorName, filmoName = filmoName)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ActorDetailScaffold(
    navController: NavController,
    actorName: String,
    actorInfoViewModel: ActorInfoViewModel = hiltViewModel(),
    filmoName: String
) {
    val actorCode = produceState(initialValue = DataOrException<ActorCode, Boolean, Exception>(loading = true)) {
        value = actorInfoViewModel.getActorCode(peopleNm = actorName, filmoNames = filmoName)
    }.value

    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController, title = actorName)
        }) {
        if (actorCode.loading == true) {
            CenterCircularProgressIndicator()
        } else if (actorCode.exception != null) {
            Log.d("로그", "ActorDetailScreen : Error while fetching data! ${actorCode.exception}")
        } else if (actorCode.data != null) {
            val actorData = produceState(initialValue = DataOrException<ActorInfo, Boolean, Exception>(loading = true)) {
                value = actorInfoViewModel.getActorInfo(peopleCd = actorCode.data!!.peopleListResult.peopleList[0].peopleCd)
            }.value

            if (actorData.loading == true) {
                CenterCircularProgressIndicator()
            } else if (actorData.exception != null) {
                Log.d("로그", "ActorDetailScreen : Error while fetching data! ${actorData.exception}")
            } else if (actorData.data != null) {
                ActorDetailMainContent(navController = navController, actorData = actorData.data!!.peopleInfoResult.peopleInfo)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ActorDetailMainContent(navController: NavController, actorData: PeopleInfo, movieViewModel: MovieViewModel = hiltViewModel()) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)) {
        Text(text = "이 배우의 다른 영화 보기", style = TextStyle(fontSize = 20.sp, color = Color.LightGray, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline))

        HorizontalPager(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(), verticalAlignment = Alignment.Top, count = actorData.filmos.size, itemSpacing = (-150).dp
        ) { page ->
            Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                MovieCard(modifier = Modifier.width(200.dp), navController = navController, rank = -1, movieCode = actorData.filmos[page].movieCd, movieBoxInfo = null)
                Text(text = actorData.filmos[page].movieNm, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.LightGray)
            }
        }
    }
}

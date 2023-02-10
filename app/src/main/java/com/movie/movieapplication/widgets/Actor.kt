package com.movie.movieapplication.widgets

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.actor.ActorInfo
import com.movie.movieapplication.model.actorcode.ActorCode
import com.movie.movieapplication.model.searchmovieinfo.Actor
import com.movie.movieapplication.screens.viewmodels.ActorInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor


@Composable
fun Actors(actorList: List<Actor>, movieBoxData: BoxOfficeInfo, navController: NavController) {
    Surface(modifier = Modifier.padding(4.dp), color = DeepMainColor, shape = RoundedCornerShape(
        CornerSize(15.dp)
    )
    ) {
        LazyRow(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(2.dp)) {
            items(items = actorList) { item ->
                ActorCard(actorInfo = item, movieBoxData = movieBoxData, navController = navController)
            }
        }
    }
}

@Composable
fun ActorCard(
    actorInfo: Actor,
    actorInfoViewModel: ActorInfoViewModel = hiltViewModel(),
    movieBoxData: BoxOfficeInfo,
    navController: NavController
) {

    val actorCode = produceState(initialValue = DataOrException<ActorCode, Boolean, Exception>(loading = true)) {
        value = actorInfoViewModel.getActorCode(peopleNm = actorInfo.peopleNm, filmoNames = movieBoxData.movieNm)
    }.value

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .width(100.dp)
            .height(70.dp)
            .clickable {
//                navController.navigate() TODO
            },
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        elevation = 4.dp,
        color = Color(0xFF222222)
    ) {
        if (actorCode.loading == true) {
            CenterCircularProgressIndicator()
        } else if (actorCode.exception != null) {
            Log.d("로그", "MovieDetailScreen: Error while fetching ActorInfoViewModel.getActorCode()")
        } else if (actorCode.data != null) {
            val actor = produceState(initialValue = DataOrException<ActorInfo, Boolean, Exception>(loading = true)){
                value = actorInfoViewModel.getActorInfo(actorCode.data!!.peopleListResult.peopleList[0].peopleCd)
            }.value

            if (actor.loading == true) {
                CenterCircularProgressIndicator()
            } else if (actor.exception != null) {
                Log.d("로그", "MovieDetailScreen: Error while fetching ActorInfoViewModel.getActorInfo()")
            } else if (actor.data != null) {
                val actorData = actor.data!!.peopleInfoResult.peopleInfo
                Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = actorData.peopleNm, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.LightGray))
                    Text(text = actorData.repRoleNm, color = Color.Gray, fontSize = 15.sp, fontWeight = FontWeight.Light)
                }
            }
        } else {
            Text(text = actorInfo.peopleNm)
        }
    }
}

package com.movie.movieapplication.widgets

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.searchmovieinfo.Actor
import com.movie.movieapplication.navigation.AllScreens
import com.movie.movieapplication.screens.viewmodels.ActorInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor


@Composable
fun Actors(actorList: List<Actor>, movieName: String, navController: NavController) {
    Surface(modifier = Modifier.padding(4.dp), color = DeepMainColor, shape = RoundedCornerShape(
        CornerSize(15.dp)
    )
    ) {
        LazyRow(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(2.dp)) {
            items(items = actorList) { item ->
                ActorCard(actorInfo = item, movieName = movieName, navController = navController)
            }
        }
    }
}

@Composable
fun ActorCard(
    actorInfo: Actor,
    actorInfoViewModel: ActorInfoViewModel = hiltViewModel(),
    movieName: String,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .width(100.dp)
            .height(70.dp)
            .clickable {
                navController.navigate(AllScreens.ActorDetailScreen.name + "?actorName=${actorInfo.peopleNm}/filmoName=${movieName}")
            },
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        elevation = 4.dp,
        color = Color(0xFF222222)
    ) {
        Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = actorInfo.peopleNm, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.LightGray))
            Text(text = if (actorInfo.cast == "") "배우" else actorInfo.cast + " 역", color = Color.Gray, fontSize = 15.sp, fontWeight = FontWeight.Light)
        }
    }
}

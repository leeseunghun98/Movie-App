package com.movie.movieapplication.widgets

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.movie.movieapplication.R
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.navigation.AllScreens
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.utils.getMovieItemsFromMovieInfo

@Composable
fun RankMovieCard(
    modifier: Modifier,
    movieInfo: BoxOfficeInfo,
    rank: Int = -1,
    navController: NavController?
) {
    Box(modifier = modifier.padding(top = 4.dp, bottom = 4.dp)) {
        MovieCard(
            modifier = Modifier.padding(top = if (rank >= 0) 15.dp else 4.dp),
            movieInfo = movieInfo,
            rank = rank,
            navController = navController
        )
        RankIcon(rank)
    }
}

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel(),
    movieInfo: BoxOfficeInfo,
    navController: NavController?,
    rank: Int
) {
    val movieName = movieInfo.movieNm
    val movieInformation =
        produceState(initialValue = DataOrException<JsonObject, Boolean, Exception>(loading = true)) {
            value = movieViewModel.getMovieInfo(movieName)
        }.value
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                val json = Uri.encode(Gson().toJson(movieInfo))
                navController?.navigate(AllScreens.MovieDetailScreen.name + "/${json}")
            }
            .background(color = DeepMainColor),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        elevation = 4.dp
    ) {
        if (movieInformation.loading == true) {
            CircularProgressIndicator()
        } else if (movieInformation.exception != null) {
            Log.d("로그", "testText.exception: ${movieInformation.exception}")
        } else if (movieInformation.data != null) {
//      TODO :       <a href="https://www.freepik.com/free-vector/contest-awards-emblems-set_14263592.htm#query=rank&position=0&from_view=search&track=sph">Image by macrovector</a> on Freepik
            val imageUrl = getMovieItemsFromMovieInfo(movieInformation).get("image").toString()
            MovieImage(imageUrl)
        }
    }
}

@Composable
private fun RankIcon(rank: Int) {
    if (rank == -1) return

    if (rank < 3) {
        val rankDrawable = when (rank) {
            0 -> R.drawable.rank1
            1 -> R.drawable.rank2
            else -> R.drawable.rank3
        }
        Image(
            painter = painterResource(id = rankDrawable),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    } else {
        Text(text = "${rank + 1}", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.LightGray))
    }
}

@Composable
fun MovieImage(imageUrl: String) {
    AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth())
}
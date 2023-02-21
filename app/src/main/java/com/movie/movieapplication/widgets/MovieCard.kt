package com.movie.movieapplication.widgets

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.movie.movieapplication.R
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.model.searchmovieinfo.MovieInfo
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.navigation.AllScreens
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.utils.countryNameToCode
import com.movie.movieapplication.utils.getMovieItemsFromMovieInfo
import com.movie.movieapplication.utils.getNations

@Composable
fun RankMovieCard(
    modifier: Modifier,
    movieBoxInfo: BoxOfficeInfo?,
    rank: Int = -1,
    movieInfo: MovieInfo? = null,
    movieCode: String? = null,
    navController: NavController?
) {
    Box(modifier = modifier.padding(top = 4.dp, bottom = 4.dp)) {
        MovieCardWithCodeOrInfo(
            modifier = Modifier.padding(top = if (rank >= 0) 15.dp else 4.dp),
            movieBoxInfo = movieBoxInfo,
            rank = rank,
            movieInfo = movieInfo,
            movieCode = movieCode,
            navController = navController
        )
        RankIcon(rank)
    }
}

@Composable
fun MovieCardWithCodeOrInfo(
    modifier: Modifier = Modifier,
    searchMovieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel(),
    movieBoxInfo: BoxOfficeInfo? = null,
    movieInfo: MovieInfo? = null,
    movieCode: String? = null,
    navController: NavController?,
    rank: Int
) {
    val movieInfo = remember {
        mutableStateOf(movieInfo)
    }
    if (movieInfo.value == null) {
        val movieInfoData =
            produceState(initialValue = DataOrException<SearchMovieInfo, Boolean, Exception>(loading = true)) {
                value = searchMovieInfoViewModel.getSearchMovieInfo(movieCode!!)
            }.value
        if (movieInfoData.data != null) {
            movieInfo.value = movieInfoData.data!!.movieInfoResult.movieInfo
        }
    }
    if (movieInfo.value != null) {
        MovieImageCard(
            modifier = modifier,
            movieBoxInfo = movieBoxInfo,
            navController = navController,
            movieInfo = movieInfo.value!!
        )
    }
}

@Composable
fun MovieImageCard(
    modifier: Modifier = Modifier,
    movieBoxInfo: BoxOfficeInfo? = null,
    navController: NavController?,
    movieInfo: MovieInfo?,
    movieInformation: JsonObject? = null,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val movieInformation = remember {
        mutableStateOf(movieInformation)
    }
    if (movieInfo != null) {
        val nation = countryNameToCode(getNations(movieInfo.nations)[0])

        if (movieInformation.value == null) {
            val movieInformationData =
                produceState(initialValue = DataOrException<JsonObject, Boolean, Exception>(loading = true)) {
                    value = movieViewModel.getMovieInfo(
                        movieName = movieInfo.movieNm,
                        country = nation,
                        movieInfo.prdtYear.toInt()
                    )
                }.value
            if (movieInformationData.loading == true) {
                CenterCircularProgressIndicator()
            } else if (movieInformationData.exception != null) {
                Log.d(
                    "로그",
                    "MovieImageCard : Error while fetching data! ${movieInformationData.exception}"
                )
            } else if (movieInformationData.data != null) {
                movieInformation.value = movieInformationData.data
            }
        }
    }
    if (movieInformation.value != null) {
        MovieCardLoading(modifier = modifier, movieBoxInfo = movieBoxInfo, navController = navController, movieInfo = movieInfo, movieInformation = movieInformation)
    }
}

@Composable
private fun MovieCardLoading(
    modifier: Modifier,
    movieBoxInfo: BoxOfficeInfo?,
    navController: NavController?,
    movieInfo: MovieInfo?,
    movieInformation: MutableState<JsonObject?>
) {
    val context = LocalContext.current
    val imageUrl = getMovieItemsFromMovieInfo(movieInformation.value!!)?.get("image") ?: ""
    if (imageUrl == "") {
        Surface(
            modifier = modifier
                .width(100.dp)
                .aspectRatio(0.707f)
                .clickable {
                    if (movieInfo != null) {
                        if (movieBoxInfo != null) {
                            val json = Uri.encode(Gson().toJson(movieBoxInfo))
                            navController?.navigate(AllScreens.MovieDetailScreen.name + "?movieCode=${movieInfo.movieCd}&movieBoxData=${json}")
                        } else {
                            navController?.navigate(AllScreens.MovieDetailScreen.name + "?movieCode=${movieInfo.movieCd}")
                        }
                    } else {
                        Toast.makeText(context, "No Data!", Toast.LENGTH_SHORT).show()
                    }
                },
            color = DeepMainColor,
            shape = RoundedCornerShape(CornerSize(15.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No Image",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
            }
        }
    } else {
        // TODO : Loading Size
        Surface(
            modifier = modifier
                .padding(4.dp)
                .clickable {
                    if (movieInfo != null) {
                        if (movieBoxInfo != null) {
                            val json = Uri.encode(Gson().toJson(movieBoxInfo))
                            navController?.navigate(AllScreens.MovieDetailScreen.name + "?movieCode=${movieInfo.movieCd}&movieBoxData=${json}")
                        } else {
                            navController?.navigate(AllScreens.MovieDetailScreen.name + "?movieCode=${movieInfo.movieCd}")
                        }
                    } else {
                        Toast.makeText(context, "No Data!", Toast.LENGTH_SHORT).show()
                    }
                },
            color = Color.Transparent,
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            shape = RoundedCornerShape(CornerSize(15.dp))
        ) {
            val imagePainter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build())
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

//      TODO :       <a href="https://www.freepik.com/free-vector/contest-awards-emblems-set_14263592.htm#query=rank&position=0&from_view=search&track=sph">Image by macrovector</a> on Freepik
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
        Text(
            text = "${rank + 1}",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
        )
    }
}
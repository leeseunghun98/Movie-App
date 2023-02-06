package com.movie.movieapplication.widgets

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.JsonObject
import com.movie.movieapplication.R
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.BoxOfficeInfo
import com.movie.movieapplication.navigation.AllScreens
import com.movie.movieapplication.screens.MovieViewModel
import com.movie.movieapplication.screens.mainscreen.MainViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.utils.getMovieItemsFromMovieInfo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieAppBar(
    navController: NavController = NavController(LocalContext.current),
    isMainScreen: Boolean = false,
    elevation: Dp = 4.dp
) {
    TopAppBar(
        title = {
            if (isMainScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DailyWeeklyMonthly(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxHeight(),
                        day = "Daily"
                    )
                    DailyWeeklyMonthly(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxHeight(),
                        day = "Weekly"
                    )
                    DailyWeeklyMonthly(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxHeight(),
                        day = "Monthly"
                    )
                }
            } else {
                // TODO
            }
        },
        actions = {
            IconButton(onClick = {
// TODO               navController.navigate()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search icon",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
// TODO                onMenuClicked()
            }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "More Icon",
                    tint = Color.White
                )
            }
        },
        backgroundColor = DeepMainColor,
        elevation = elevation,
        modifier = Modifier.padding(top = 6.dp, start = 6.dp, end = 6.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DailyWeeklyMonthly(
    modifier: Modifier,
    day: String,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Surface(
        modifier = modifier.clickable {
            mainViewModel.updateBoxOffice(day)
        },
        shape = RoundedCornerShape(
            CornerSize(12.dp)
        ), color = Color.Black
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day,
                color = Color.White,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


package com.movie.movieapplication.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.movieapplication.navigation.AllScreens
import com.movie.movieapplication.screens.mainscreen.MainViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieAppBar(
    navController: NavController = NavController(LocalContext.current),
    isMainScreen: Boolean = false,
    elevation: Dp = 4.dp,
    title: String = ""
) {
    TopAppBar(
        title = {
            if (isMainScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .fillMaxHeight()
                    DailyWeeklyMonthly(
                        modifier = modifier,
                        day = "Daily"
                    )
                    DailyWeeklyMonthly(
                        modifier = modifier,
                        day = "Weekly"
                    )
                    DailyWeeklyMonthly(
                        modifier = modifier,
                        day = "Monthly"
                    )
                }
            } else {
                Text(text = title, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.LightGray, fontStyle = FontStyle.Italic))
            }
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    navController.navigate(AllScreens.SearchScreen.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search icon",
                        tint = Color.White
                    )
                }
//                IconButton(onClick = {
//
//                }) {
//                    Icon(
//                        imageVector = Icons.Rounded.MoreVert,
//                        contentDescription = "More Icon",
//                        tint = Color.White
//                    )
//                }
            }
        },
        navigationIcon = if (!isMainScreen) {
            @Composable {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    BackArrow()
                }
            }
        } else null,
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


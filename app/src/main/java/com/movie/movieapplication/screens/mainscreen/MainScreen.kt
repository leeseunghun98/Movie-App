package com.movie.movieapplication.screens.mainscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.DailyBoxOffice
import com.movie.movieapplication.utils.getToday

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val data = produceState<DataOrException<DailyBoxOffice, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getDailyBoxOffice()
    }.value
    Log.d("로그", "Today : ${getToday()}")
    if (data.loading == true) {
        Log.d("로그", "Loading ...");
        CircularProgressIndicator()
    } else if (data.data != null) {
        Log.d("로그", "Finished fetching data");
        Text(text = data.data.toString())
    }
}
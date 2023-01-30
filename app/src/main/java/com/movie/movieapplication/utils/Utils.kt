package com.movie.movieapplication.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
fun getToday(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val cur = LocalDate.now().minusDays(1)
    return cur.format(formatter)
}
package com.movie.movieapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getYesterday(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val cur = LocalDate.now().minusDays(1)
    return cur.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getLastWeek(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val cur = LocalDate.now().minusWeeks(1)
    return cur.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getLastMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val cur = LocalDate.now().minusMonths(1)
    return cur.format(formatter)
}

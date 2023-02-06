package com.movie.movieapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import org.json.JSONArray
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

fun getMovieItemsFromMovieInfo(movieList: DataOrException<JsonObject, Boolean, Exception>) =
    JSONArray(movieList.data!!.get("items").toString()).getJSONObject(0)

fun String.bstrip(): String {
    return this.replace("<b>", "").replace("</b>", "")
}
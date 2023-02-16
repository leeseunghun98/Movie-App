package com.movie.movieapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextIndent
import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.searchmovieinfo.*
import org.json.JSONArray
import org.json.JSONObject
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

fun getMovieItemsFromMovieInfo(movieList: JsonObject): JSONObject? {
    val json = JSONArray(movieList.get("items").toString())
    if (json.length() > 0) {
        return json.getJSONObject(0)
    }
    return null
}

fun String.bstrip(): String {
    return this.replace("<b>", "").replace("</b>", "")
}

fun getDirector(directors: List<Director>): List<String> = directors.map { it.peopleNm }

fun getGenre(genres: List<Genre>): List<String> = genres.map { it.genreNm }

fun getNations(nations: List<Nation>): List<String> = nations.map { it.nationNm }

fun getGradeNm(audits: List<Audit>): Set<String> = audits.map { it.watchGradeNm }.toSet()

fun getActors(movieInformation: SearchMovieInfo): List<Actor> {
    return movieInformation.movieInfoResult.movieInfo.actors
}

fun getIntenAnnotatedString(string: String, num: Int, inten: Int): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = Color.LightGray
                )
            ) {
                append("$string : ${formatNumber(num.toLong())}")
            }
            if (inten == 0) {
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray
                    )
                ) {
                    append(" =")
                }
            } else if (inten > 0) {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue
                    )
                ) {
                    append(" ▲ $inten")
                }
            } else {
                withStyle(
                    style = SpanStyle(
                        color = Color.Red
                    )
                ) {
                    append(" ▼ $inten")
                }
            }
        }
    }
}

fun formatNumber(num: Long, isMoney: Boolean = false): String {
    val won = num % 10000
    val man = (num / 10000) % 10
    val eok = num / 100000000

    val eokString = if (eok > 0) "${eok}억 " else ""
    val manString = if (man > 0) "${man}만 " else ""

    return if (isMoney) "$eokString$manString${won}원" else "$eokString$manString$won"
}

fun countryNameToCode(countryName: String): String {
    return when (countryName) {
        "한국" -> "KR"
        "프랑스" -> "FR"
        "영국" -> "GB"
        "일본" -> "JP"
        "미국" -> "US"
        "홍콩" -> "HK"
        else -> "ETC"
    }
}
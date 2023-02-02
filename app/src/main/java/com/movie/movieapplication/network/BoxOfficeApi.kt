package com.movie.movieapplication.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.movie.movieapplication.model.daily.DailyBox
import com.movie.movieapplication.model.weekly.WeekBox
import com.movie.movieapplication.utils.ApiKeys
import com.movie.movieapplication.utils.getLastMonth
import com.movie.movieapplication.utils.getLastWeek
import com.movie.movieapplication.utils.getYesterday
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
interface BoxOfficeApi {
    @GET(value = "boxoffice/searchDailyBoxOfficeList.json")
    suspend fun getDailyBoxOfficeList(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("targetDt") targetDt: String = getYesterday()
    ): DailyBox

    @GET(value = "boxoffice/searchWeeklyBoxOfficeList.json")
    suspend fun getWeeklyBoxOfficeList(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("targetDt") targetDt: String = getLastWeek(),
        @Query("weekGb") weekGb: String = "0"
    ): WeekBox

    @GET(value = "boxoffice/searchWeeklyBoxOfficeList.json")
    suspend fun getMontlyBoxOfficeList(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("targetDt") targetDt: String = getLastMonth(),
        @Query("weekGb") weekGb: String = "0"
    ): WeekBox
}
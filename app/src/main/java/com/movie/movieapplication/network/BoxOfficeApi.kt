package com.movie.movieapplication.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.movie.movieapplication.model.DailyBoxOffice
import com.movie.movieapplication.utils.ApiKeys
import com.movie.movieapplication.utils.getToday
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BoxOfficeApi {
    @RequiresApi(Build.VERSION_CODES.O)
    @GET(value = "boxoffice/searchDailyBoxOfficeList.json")
    suspend fun getDailyBoxOfficeList(
        @Query("key") key: String = ApiKeys.API_KEY,
        @Query("targetDt") targetDt: String = getToday()
    ): DailyBoxOffice
}
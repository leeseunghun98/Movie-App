package com.movie.movieapplication.network

import com.google.gson.JsonObject
import com.movie.movieapplication.utils.ApiKeys
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MovieApi {
    @GET(value = "movie")
    @Headers(
        "X-Naver-Client-Id: ${ApiKeys.CLIENT_ID}",
        "X-Naver-Client-Secret: ${ApiKeys.CLIENT_SECRET}"
    )
    suspend fun getMovieData(
        @Query("query") movieName: String,
        @Query("country") country: String,
        @Query("yearfrom") yearfrom: Int,
        @Query("yearto") yearto: Int
    ): JsonObject

    @GET(value = "movie")
    @Headers(
        "X-Naver-Client-Id: ${ApiKeys.CLIENT_ID}",
        "X-Naver-Client-Secret: ${ApiKeys.CLIENT_SECRET}"
    )
    suspend fun searchMovies(
        @Query("query") movieName: String,
    ): JsonObject
}
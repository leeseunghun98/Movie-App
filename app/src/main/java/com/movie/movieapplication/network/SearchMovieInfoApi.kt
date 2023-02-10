package com.movie.movieapplication.network

import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.utils.ApiKeys
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface SearchMovieInfoApi {
    @GET(value = "movie/searchMovieInfo.json")
    suspend fun getSearchMovieInfo(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("movieCd") movieCd: String
        ): SearchMovieInfo
}
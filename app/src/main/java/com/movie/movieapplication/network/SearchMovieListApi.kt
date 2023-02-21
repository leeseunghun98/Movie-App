package com.movie.movieapplication.network

import com.movie.movieapplication.model.searchmovielist.SearchMovieList
import com.movie.movieapplication.utils.ApiKeys
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface SearchMovieListApi {
    @GET(value = "movie/searchMovieList.json")
    suspend fun getSearchMovieList(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("movieNm") movieNm: String,
        @Query("directorNm") directorNm: String
    ): SearchMovieList
}
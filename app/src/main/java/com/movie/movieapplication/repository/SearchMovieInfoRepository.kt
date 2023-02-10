package com.movie.movieapplication.repository

import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.network.SearchMovieInfoApi
import javax.inject.Inject

class SearchMovieInfoRepository @Inject constructor(private val api: SearchMovieInfoApi){
    suspend fun getSearchMovieInfo(movieCd: String): DataOrException<SearchMovieInfo, Boolean, Exception> {
        val response = try {
            api.getSearchMovieInfo(movieCd = movieCd)
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }
}
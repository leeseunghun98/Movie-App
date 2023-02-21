package com.movie.movieapplication.repository

import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.searchmovielist.SearchMovieList
import com.movie.movieapplication.network.SearchMovieListApi
import javax.inject.Inject

class SearchMovieListRepository @Inject constructor(private val api: SearchMovieListApi) {
    suspend fun getSearchMovieList(movieNm: String, directorNm: String): DataOrException<SearchMovieList, Boolean, Exception> {
        val response = try {
            api.getSearchMovieList(movieNm = movieNm, directorNm = directorNm)
        } catch (e: Exception) {
            return DataOrException(exception = e)
        }
        return DataOrException(data = response)
    }
}
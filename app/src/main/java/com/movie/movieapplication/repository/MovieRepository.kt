package com.movie.movieapplication.repository

import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.network.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi){
    suspend fun getMovieInfo(movie: String): DataOrException<JsonObject, Boolean, Exception> {
        val response = try {
            api.getMovieData(movie)
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }
}
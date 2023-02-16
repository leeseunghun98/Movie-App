package com.movie.movieapplication.repository

import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.network.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi){
    suspend fun getMovieInfo(movieName: String, country: String, year: Int): DataOrException<JsonObject, Boolean, Exception> {
        val response = try {
            api.getMovieData(movieName = movieName, country = country, yearfrom = year, yearto = year)
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }

    suspend fun searchMovies(movieName: String): DataOrException<JsonObject, Boolean, Exception> {
        val response = try {
            api.searchMovies(movieName = movieName)
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }
}
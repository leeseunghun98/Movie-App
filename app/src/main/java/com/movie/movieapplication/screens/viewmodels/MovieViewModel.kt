package com.movie.movieapplication.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MovieViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {
    suspend fun getMovieInfo(movieName: String): DataOrException<JsonObject, Boolean, Exception> {
        return repository.getMovieInfo(movieName)
    }
}
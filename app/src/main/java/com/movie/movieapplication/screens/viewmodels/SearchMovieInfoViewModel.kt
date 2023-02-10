package com.movie.movieapplication.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.repository.SearchMovieInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMovieInfoViewModel @Inject constructor(private val repository: SearchMovieInfoRepository): ViewModel() {
    suspend fun getSearchMovieInfo(movieCd: String): DataOrException<SearchMovieInfo, Boolean, Exception> {
        return repository.getSearchMovieInfo(movieCd = movieCd)
    }
}
package com.movie.movieapplication.screens.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.searchmovielist.SearchMovieList
import com.movie.movieapplication.repository.MovieRepository
import com.movie.movieapplication.repository.SearchMovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository, private val searchMovieListRepository: SearchMovieListRepository) : ViewModel() {
    private val _movieList = MutableStateFlow<DataOrException<JsonObject, Boolean, Exception>>(
        DataOrException(exception = null, data = null, loading = false)
    )
    val movieList = _movieList.asStateFlow()

    fun updateSearchBarWithMovieName(movieName: String) {
        _movieList.value.loading = true
        _movieList.value.exception = null
        _movieList.value.data = null
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = movieRepository.searchMovies(movieName = movieName)
        }
    }

    suspend fun getSearchMovieList(movieNm: String, directorNm: String): DataOrException<SearchMovieList, Boolean, Exception> {
        return searchMovieListRepository.getSearchMovieList(movieNm = movieNm, directorNm = directorNm)
    }
}
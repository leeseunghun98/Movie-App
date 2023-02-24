package com.movie.movieapplication.screens.searchscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
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
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository, private val searchMovieListRepository: SearchMovieListRepository) : ViewModel() {
    private val _movieList = MutableStateFlow<List<JsonObject>>(emptyList())
    val movieList = _movieList.asStateFlow()

    private val _state = MutableStateFlow(LoadingState.IDLE)
    val state = _state.asStateFlow()

    fun updateSearchMovieList(movieName: String) {
        _state.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            val movieListData = movieRepository.searchMovies(movieName = movieName)
            if (movieListData.data != null) {
                Log.d("로그", movieListData.data.toString())
                val jsonArray = JSONArray(movieListData.data!!.get("items").toString())
                val movieItems = (0 until jsonArray.length()).map { index ->
                    JsonObject().apply {
                        val item: JsonObject = Gson().fromJson(jsonArray.get(index).toString(), JsonObject::class.java)
                        add("items", JsonArray().apply{add(item)})
                    }
                }
                _movieList.value = movieItems
            }
            _state.value = LoadingState.IDLE
        }
    }

    suspend fun getSearchMovieList(movieNm: String, directorNm: String): DataOrException<SearchMovieList, Boolean, Exception> {
        return searchMovieListRepository.getSearchMovieList(movieNm = movieNm, directorNm = directorNm)
    }
}
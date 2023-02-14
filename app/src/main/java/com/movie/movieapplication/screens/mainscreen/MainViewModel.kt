package com.movie.movieapplication.screens.mainscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.DailyOrWeekly
import com.movie.movieapplication.repository.BoxOfficeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: BoxOfficeRepository) : ViewModel() {
    private val _mode = MutableStateFlow("Daily")
    val mode = _mode.asStateFlow()

    private val _movieList =
        MutableStateFlow<DataOrException<DailyOrWeekly, Boolean, Exception>>(DataOrException(loading = true))
    val movieList = _movieList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = getBoxOffice("Daily")
        }
    }

    private suspend fun getDailyBoxOffice(): DataOrException<DailyOrWeekly, Boolean, Exception> {
        return repository.getDailyBoxOffice()
    }

    private suspend fun getWeeklyBoxOffice(): DataOrException<DailyOrWeekly, Boolean, Exception> {
        return repository.getWeeklyBoxOffice()
    }

    private suspend fun getMonthlyBoxOffice(): DataOrException<DailyOrWeekly, Boolean, Exception> {
        return repository.getMonthlyBoxOffice()
    }

    suspend fun getBoxOffice(mode: String): DataOrException<DailyOrWeekly, Boolean, Exception> {
        return when (mode) {
            "Daily" -> getDailyBoxOffice()
            "Weekly" -> getWeeklyBoxOffice()
            "Monthly" -> getMonthlyBoxOffice()
            else -> getDailyBoxOffice()
        }
    }

    fun updateBoxOffice(s: String) {
        _mode.value = s
        _movieList.value.loading = true
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = getBoxOffice(s)
        }
    }
}


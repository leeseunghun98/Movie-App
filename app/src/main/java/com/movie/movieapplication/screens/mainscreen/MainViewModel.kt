package com.movie.movieapplication.screens.mainscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.DailyBoxOffice
import com.movie.movieapplication.repository.BoxOfficeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: BoxOfficeRepository) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDailyBoxOffice(): DataOrException<DailyBoxOffice, Boolean, Exception> {
        return repository.getDailyBoxOffice()
    }
}
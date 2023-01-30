package com.movie.movieapplication.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.DailyBoxOffice
import com.movie.movieapplication.network.BoxOfficeApi
import javax.inject.Inject

class BoxOfficeRepository @Inject constructor(private val api: BoxOfficeApi) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDailyBoxOffice(): DataOrException<DailyBoxOffice, Boolean, Exception> {
        val response = try {
            api.getDailyBoxOfficeList()
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }
}
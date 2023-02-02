package com.movie.movieapplication.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.DailyOrWeekly
import com.movie.movieapplication.network.BoxOfficeApi
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class BoxOfficeRepository @Inject constructor(private val api: BoxOfficeApi) {
    suspend fun getDailyBoxOffice(): DataOrException<DailyOrWeekly, Boolean, Exception> {
        val response = try {
            api.getDailyBoxOfficeList()
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = DailyOrWeekly.Daily(response))
    }

    suspend fun getWeeklyBoxOffice(): DataOrException<DailyOrWeekly, Boolean, Exception> {
        val response = try {
            api.getWeeklyBoxOfficeList()
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = DailyOrWeekly.Weekly(response))
    }

    suspend fun getMonthlyBoxOffice(): DataOrException<DailyOrWeekly, Boolean, Exception> {
        val response = try {
            api.getMontlyBoxOfficeList()
        } catch (exception: Exception) {
            return DataOrException(exception = exception)
        }
        return DataOrException(data = DailyOrWeekly.Weekly(response))
    }
}
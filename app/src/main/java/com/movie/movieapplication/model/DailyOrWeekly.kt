package com.movie.movieapplication.model

import com.movie.movieapplication.model.daily.DailyBox
import com.movie.movieapplication.model.weekly.WeekBox

sealed class DailyOrWeekly {
    data class Daily(val boxOffice: DailyBox) : DailyOrWeekly()
    data class Weekly(val boxOffice: WeekBox) : DailyOrWeekly()
}

val DailyOrWeekly.boxOfficeList: List<BoxOfficeInfo>
    get() = when (this) {
        is DailyOrWeekly.Daily -> this.boxOffice.boxOfficeResult.dailyBoxOfficeList
        is DailyOrWeekly.Weekly -> this.boxOffice.boxOfficeResult.weeklyBoxOfficeList
    }
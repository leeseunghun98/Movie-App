package com.movie.movieapplication.model.weekly

import com.movie.movieapplication.model.BoxOfficeInfo

data class BoxOfficeResult(
    val boxofficeType: String,
    val showRange: String,
    val weeklyBoxOfficeList: List<BoxOfficeInfo>,
    val yearWeekTime: String
)
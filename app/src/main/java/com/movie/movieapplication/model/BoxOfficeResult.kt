package com.movie.movieapplication.model

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<DailyBoxOfficeList>,
    val showRange: String
)
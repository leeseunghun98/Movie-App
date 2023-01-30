package com.movie.movieapplication.model

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeMovieList: List<DailyBoxOfficeMovie>,
    val showRange: String
)
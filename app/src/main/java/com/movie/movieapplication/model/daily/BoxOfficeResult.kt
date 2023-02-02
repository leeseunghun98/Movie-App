package com.movie.movieapplication.model.daily

import com.movie.movieapplication.model.BoxOfficeInfo

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<BoxOfficeInfo>,
    val showRange: String
)
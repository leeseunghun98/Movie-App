package com.movie.movieapplication.model.searchmovielist

data class Movie(
    val companys: List<Company>,
    val directors: List<Director>,
    val genreAlt: String,
    val movieCd: String,
    val movieNm: String,
    val movieNmEn: String,
    val nationAlt: String,
    val openDt: String,
    val prdtStatNm: String,
    val prdtYear: String,
    val repGenreNm: String,
    val repNationNm: String,
    val typeNm: String
)
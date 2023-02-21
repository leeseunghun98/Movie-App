package com.movie.movieapplication.model.searchmovielist

data class MovieListResult(
    val movieList: List<Movie>,
    val source: String,
    val totCnt: Int
)
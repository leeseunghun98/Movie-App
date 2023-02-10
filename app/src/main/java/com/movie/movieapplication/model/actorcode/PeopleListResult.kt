package com.movie.movieapplication.model.actorcode

data class PeopleListResult(
    val peopleList: List<People>,
    val source: String,
    val totCnt: Int
)
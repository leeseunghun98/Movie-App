package com.movie.movieapplication.model.actor

data class PeopleInfo(
    val filmos: List<Filmo>,
    val homepages: List<Any>,
    val peopleCd: String,
    val peopleNm: String,
    val peopleNmEn: String,
    val repRoleNm: String,
    val sex: String
)
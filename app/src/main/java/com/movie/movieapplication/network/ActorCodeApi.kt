package com.movie.movieapplication.network

import com.movie.movieapplication.model.actorcode.ActorCode
import com.movie.movieapplication.utils.ApiKeys
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ActorCodeApi {
    @GET(value = "people/searchPeopleList.json")
    suspend fun getActorCode(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("filmoNames") filmoNames: String,
        @Query("peopleNm") peopleNm: String,
        @Query("itemPerPage") itemPerPage: String = "1"
    ): ActorCode
}
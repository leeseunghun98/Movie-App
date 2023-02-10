package com.movie.movieapplication.network

import com.movie.movieapplication.model.actor.ActorInfo
import com.movie.movieapplication.utils.ApiKeys
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ActorApi {
    @GET(value = "people/searchPeopleInfo.json")
    suspend fun getActorInfo(
        @Query("key") key: String = ApiKeys.BOXOFFICE_API_KEY,
        @Query("peopleCd") peopleCd: String
    ): ActorInfo

}
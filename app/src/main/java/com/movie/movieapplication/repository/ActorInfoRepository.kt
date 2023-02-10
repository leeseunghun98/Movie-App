package com.movie.movieapplication.repository

import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.actor.ActorInfo
import com.movie.movieapplication.model.actorcode.ActorCode
import com.movie.movieapplication.network.ActorApi
import com.movie.movieapplication.network.ActorCodeApi
import javax.inject.Inject

class ActorInfoRepository @Inject constructor(private val actorInfoapi: ActorApi, private val actorCodeApi: ActorCodeApi){
    suspend fun getActorCode(peopleNm: String, filmoNames: String): DataOrException<ActorCode, Boolean, Exception> {
        val response = try {
            actorCodeApi.getActorCode(peopleNm = peopleNm, filmoNames = filmoNames)
        } catch (e: Exception) {
            return DataOrException(exception = e)
        }
        return DataOrException(data = response)
    }

    suspend fun getActorInfo(peopleCd: String): DataOrException<ActorInfo, Boolean, Exception> {
        val response = try {
            actorInfoapi.getActorInfo(peopleCd = peopleCd)
        } catch (e: Exception) {
            return DataOrException(exception = e)
        }
        return DataOrException(data = response)
    }
}
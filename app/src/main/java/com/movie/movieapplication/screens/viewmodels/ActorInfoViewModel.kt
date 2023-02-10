package com.movie.movieapplication.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.actor.ActorInfo
import com.movie.movieapplication.model.actorcode.ActorCode
import com.movie.movieapplication.repository.ActorInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorInfoViewModel @Inject constructor(private val repository: ActorInfoRepository): ViewModel() {
    suspend fun getActorCode(peopleNm: String, filmoNames: String): DataOrException<ActorCode, Boolean, Exception> {
        return repository.getActorCode(peopleNm = peopleNm, filmoNames = filmoNames)
    }

    suspend fun getActorInfo(peopleCd: String): DataOrException<ActorInfo, Boolean, Exception> {
        return repository.getActorInfo(peopleCd = peopleCd)
    }
}
package com.movie.movieapplication.di

import com.movie.movieapplication.network.BoxOfficeApi
import com.movie.movieapplication.utils.ApiKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideBoxOfficeApi(): BoxOfficeApi {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoxOfficeApi::class.java)
    }

}
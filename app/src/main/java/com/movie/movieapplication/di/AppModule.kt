package com.movie.movieapplication.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.movie.movieapplication.network.BoxOfficeApi
import com.movie.movieapplication.network.MovieApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideBoxOfficeApi(): BoxOfficeApi {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.BOXOFFICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoxOfficeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

}
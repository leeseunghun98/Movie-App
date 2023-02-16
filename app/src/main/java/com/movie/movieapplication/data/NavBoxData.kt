package com.movie.movieapplication.data

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.movie.movieapplication.model.BoxOfficeInfo

class NavBoxData : NavType<BoxOfficeInfo>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): BoxOfficeInfo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): BoxOfficeInfo {
        return Gson().fromJson(value, BoxOfficeInfo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: BoxOfficeInfo) {
        bundle.putParcelable(key, value)
    }
}
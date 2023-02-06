package com.movie.movieapplication.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.movie.movieapplication.ui.theme.MainColor

@Composable
fun BasicScreen(inSide: @Composable () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = MainColor) {
        inSide()
    }
}
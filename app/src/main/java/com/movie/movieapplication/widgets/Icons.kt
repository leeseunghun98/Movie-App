package com.movie.movieapplication.widgets

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BackArrow() {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = "Back",
        tint = Color.LightGray
    )
}
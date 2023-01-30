package com.movie.movieapplication.screens.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.movie.movieapplication.R
import com.movie.movieapplication.navigation.AllScreens
import com.movie.movieapplication.ui.theme.MainColor
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }
    val random = Random().nextInt(2)

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(durationMillis = 1000,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                })
        )
        delay(2000L)
        navController.navigate(AllScreens.MainScreen.name)
    })

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MainColor,
    ) {
        Image(
            painter = painterResource(
                id = when (random) {
                    0 -> R.drawable.camera
                    else -> R.drawable.popcorn
                }
            ),
            contentDescription = "splash",
            modifier = Modifier.scale(scale.value),
            contentScale = ContentScale.Fit
        )
    }
}
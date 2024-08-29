package com.example.quizzard.presentation.screens.loginWithName.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quizzard.R

@Composable
fun LottieAnimationIntro() {
    rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_lnudoydi)
    ).value.let { composition ->
        LottieAnimation(
            composition,
            modifier = Modifier.size(200.dp),
            alignment = Alignment.Center,
            iterations = 1
        )
    }
}
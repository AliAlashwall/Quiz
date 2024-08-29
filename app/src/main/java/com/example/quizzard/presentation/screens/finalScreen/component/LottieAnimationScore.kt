package com.example.quizzard.presentation.screens.finalScreen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quizzard.R

@Composable
fun LottieAnimationScore(score: Int) {
    val duration = if (score < 5) 0.55f else if (score in 5..7) 0.71f else 1f
    rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_score)
    ).value.let { composition ->
        LottieAnimation(
            composition,
            clipSpec = LottieClipSpec.Progress(max = duration),
            modifier = Modifier
                .padding(top = 40.dp)
                .size(250.dp),
            alignment = Alignment.Center,


            )
    }
}
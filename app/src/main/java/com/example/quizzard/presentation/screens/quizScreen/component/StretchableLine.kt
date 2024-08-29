package com.example.quizzard.presentation.screens.quizScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizzard.presentation.theme.trueAnswer

@Composable
fun StretchableLine(
    width: Dp = 40.dp, color: Color = trueAnswer
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color = Color(0x66F6D1AB), RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(10.dp)
                .background(color, RoundedCornerShape(10.dp))
        )
    }
}
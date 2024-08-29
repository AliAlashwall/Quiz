package com.example.quizzard.presentation.screens.finalScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizzard.R
import com.example.quizzard.presentation.screens.finalScreen.component.LottieAnimationCeleb
import com.example.quizzard.presentation.screens.finalScreen.component.LottieAnimationScore
import com.example.quizzard.presentation.theme.QuizMasterTheme

@Composable
fun FinalScreen(
    userName: String,
    score: Int,
    onTryAgainClicked: () -> Unit,
    listSize: Int,
    resetQuiz: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LottieAnimationScore(score)

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Quiz Result",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(100.dp))

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LottieAnimationCeleb()

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "Name : $userName",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Score : $score/$listSize",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = {
                        onTryAgainClicked()
                        resetQuiz()
                    },
                    modifier = Modifier
                        .padding(top = 60.dp, end = 16.dp, start = 16.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.try_again),
                        fontSize = 20.sp
                    )
                }
            }

        }
    }
    BackHandler {
        // not allowed to back
    }
}

@Preview(showSystemUi = true)
@Composable
fun FinalPreview() {
    QuizMasterTheme {
        FinalScreen("Ali", 5, {}, 10) {
        }
    }
}

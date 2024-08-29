package com.example.quizzard.presentation.screens.quizScreen.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizzard.presentation.theme.falseAnswer
import com.example.quizzard.presentation.theme.trueAnswer

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AnswerItem(
    listAnswer: List<String>,
    correctAnswer: String,
    selectedAnswer: String,
    solved: Boolean,
    currentSelectedItem: Int,
    itemIndex: Int,
    onItemClicked: (Int) -> Unit,
    incScore: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                if (!solved) {
                    onItemClicked(itemIndex)
                    if (selectedAnswer == correctAnswer) {
                        incScore()
                    }
                }
            },
        colors = if (solved && selectedAnswer == correctAnswer) {
            CardDefaults.cardColors(trueAnswer)
        } else if (solved && (itemIndex == currentSelectedItem)) {
            CardDefaults.cardColors(falseAnswer)
        } else CardDefaults.cardColors(Color.Unspecified),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${(listAnswer.indexOf(selectedAnswer) + 1)}.",
                Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = selectedAnswer,
                Modifier.fillMaxWidth(),
            )
        }
    }
}


package com.example.quizzard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.example.quizzard.QuizViewModel
import com.example.quizzard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    quizViewModel: QuizViewModel = viewModel(),
    onNextClicked : ()-> Unit,
) {
    val gameUiState by quizViewModel.gameUiState.collectAsState()

    val focusManager = LocalFocusManager.current
    var uName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            Row(
                modifier = Modifier
                    .padding(top = 60.dp, bottom = 160.dp)
                    .size(200.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                LottieAnimationExample()
            }


        OutlinedTextField(
            value = gameUiState.userName,
            onValueChange = {name ->
                quizViewModel.updateUserName(name)
                uName = name
            },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            label = {
                Text("Enter your name")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    quizViewModel.updateUserName(uName)
                }
            )
        )
        Button(
            onClick = {
                onNextClicked()
            },
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 50.dp)
                .width(300.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF3BB81))
        ) {
            Text(text = "Start Quiz")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun StartPreview() {
    StartScreen {}
}


@Composable
fun LottieAnimationExample() {

    rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_lnudoydi)
    ).value.let { composition ->
        LottieAnimation(
            composition,
            modifier = Modifier.
                size(200.dp),
            alignment = Alignment.Center,
            iterations = 1

            )
    }
}
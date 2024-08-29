package com.example.quizzard.presentation.screens.loginWithName

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizzard.R
import com.example.quizzard.presentation.screens.QuizViewModel
import com.example.quizzard.presentation.screens.loginWithName.component.LottieAnimationIntro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWithNameScreen(
    quizViewModel: QuizViewModel,
    onNextClicked: () -> Unit,
) {
    val quizUiState by quizViewModel.quizUiState.collectAsState()

    val focusManager = LocalFocusManager.current
    var uName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 160.dp)
                .size(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LottieAnimationIntro()
        }

        OutlinedTextField(
            value = quizUiState.userName,
            onValueChange = { name ->
                quizViewModel.updateUserName(name)
                uName = name
            },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            label = {
                Text(stringResource(R.string.enter_your_name))
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
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(R.string.start_quiz))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginWithNamePreview() {
    val vm = viewModel<QuizViewModel>()
    LoginWithNameScreen (vm){}
}
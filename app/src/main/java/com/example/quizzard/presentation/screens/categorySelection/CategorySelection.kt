package com.example.quizzard.presentation.screens.categorySelection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizzard.R
import com.example.quizzard.presentation.screens.QuizViewModel
import com.example.quizzard.presentation.screens.categorySelection.component.CategoryItem
import com.example.quizzard.presentation.screens.categorySelection.component.CategoryTopAppBar
import com.example.quizzard.presentation.screens.categorySelection.component.DailyQuizItem
import com.example.quizzard.presentation.screens.models.Subject
import com.example.quizzard.presentation.theme.QuizMasterTheme

@Composable
fun CategorySelection(
    quizViewModel: QuizViewModel,
    navToQuiz: () -> Unit
) {
    val quizUiState by quizViewModel.quizUiState.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryTopAppBar(name = quizUiState.userName)

        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            text = stringResource(R.string.practice_more),
            fontWeight = FontWeight.Bold
        )

        DailyQuizItem {
            quizViewModel.onSubjectClicked(Subject.Daily)
            navToQuiz()
        }


        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.specific_category),
            fontWeight = FontWeight.Bold
        )
        CategoryItem(
            category = stringResource(R.string.sports),
            classNum = "A",
            onCategoryClicked = {
                quizViewModel.onSubjectClicked(Subject.Sports)
                navToQuiz()
            },
        )
        CategoryItem(
            category = stringResource(R.string.programing),
            classNum = "B",
            onCategoryClicked = {
                quizViewModel.onSubjectClicked(Subject.Programing)
                navToQuiz()
            },
        )
        CategoryItem(
            category = stringResource(R.string.math),
            classNum = "C",
            onCategoryClicked = {
                quizViewModel.onSubjectClicked(Subject.Math)
                navToQuiz()
            },
        )
        CategoryItem(
            category = stringResource(R.string.history),
            classNum = "D",
            onCategoryClicked = {
                quizViewModel.onSubjectClicked(Subject.History)
                navToQuiz()
            },
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun CategorySelectionPrev() {
    QuizMasterTheme {
        val quizViewModel: QuizViewModel = viewModel()
        CategorySelection(quizViewModel) {}
    }
}
package com.example.quizzard.presentation.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quizzard.R
import com.example.quizzard.domain.model.Question
import com.example.quizzard.presentation.screens.QuizUiState
import com.example.quizzard.presentation.screens.QuizViewModel
import com.example.quizzard.presentation.screens.models.GameUiState
import com.example.quizzard.presentation.theme.QuizMasterTheme
import com.example.quizzard.presentation.theme.falseAnswer
import com.example.quizzard.presentation.theme.trueAnswer

@Composable
fun HomeScreen(
    quizViewModel: QuizViewModel, navToFinalScreen: () -> Unit, navBack: () -> Unit
) {

    when (val quizUiState = quizViewModel.quizUiState) {
        is QuizUiState.Success -> GameScreen(
            quizUiState.question.results, quizViewModel, navToFinalScreen
        ) { quizViewModel.backToHome { navBack() } }

        is QuizUiState.Loading -> LoadingScreen()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(
    questionList: List<Question>,
    quizViewModel: QuizViewModel,
    navToFinalScreen: () -> Unit,
    navBack: () -> Unit
) {

    quizViewModel.getQuestionDetails(questionList)
    val gameUiState by quizViewModel.gameUiState.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GameLayout(question = gameUiState.question,
            quizViewModel = quizViewModel,
            gameUiState = gameUiState,
            listAnswer = gameUiState.listOfAnswer,
            correctAnswer = gameUiState.correctAnswer,
            clicked = gameUiState.clicked,
            score = gameUiState.score,
            counter = gameUiState.counter,
            listSize = gameUiState.questionListSize,
            onNextClick = { quizViewModel.onNextClick(navToFinalScreen) },
            navBack = { navBack() })
    }
    BackHandler {
        // use only upper back button to back
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun GameLayout(
    question: String,
    quizViewModel: QuizViewModel,
    gameUiState: GameUiState,
    listAnswer: List<String>,
    correctAnswer: String,
    clicked: Boolean,
    score: Int,
    counter: Int,
    listSize: Int,
    onNextClick: () -> Unit,
    navBack: () -> Unit
) {
    val shuffledListAnswer = remember(listAnswer) { listAnswer.shuffled() }
    Log.v("MainActivity", correctAnswer)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopAppBar("${gameUiState.category}", score, listSize, navBack)

        QuestionCard(question, counter, listSize)

        Spacer(modifier = Modifier.weight(1f)/*height(25.dp)*/)

        AnswersList(
            listAnswer = shuffledListAnswer,
            correctAnswer = correctAnswer,
            solved = clicked,
            onItemClicked = { quizViewModel.onItemClicked(it) },
            incScore = { quizViewModel.incScore() },
            currentSelectedItem = quizViewModel.gameUiState.value.itemIndexed
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onNextClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),

            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.next_question),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
fun QuestionCard(
    question: String, counter: Int, listSize: Int
) {
    var textSize by remember { mutableStateOf(20.sp) }
    if (question.length > 120) textSize = 18.sp else 20.sp
    Card(

        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp)
            .size(350.dp, 160.dp),
        colors = CardDefaults.cardColors(Color.Unspecified),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(Color(0xFFF6D1AB))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = "${counter + 1}/$listSize",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.End
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = question,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize,
                    lineHeight = 28.sp
                )
            }
        }
    }
}

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

@Composable
fun AnswersList(
    listAnswer: List<String>,
    correctAnswer: String,
    solved: Boolean,
    currentSelectedItem: Int,
    onItemClicked: (Int) -> Unit,
    incScore: () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(listAnswer) { index, possibleAnswer ->

            AnswerItem(
                listAnswer = listAnswer,
                correctAnswer = correctAnswer,
                selectedAnswer = possibleAnswer,
                solved = solved,
                itemIndex = index,
                currentSelectedItem = currentSelectedItem,
                onItemClicked = { onItemClicked(index) },
                incScore = incScore
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading_animation)
    ).value.let { composition ->
        LottieAnimation(
            composition,
            modifier = Modifier.size(200.dp),
            alignment = Alignment.Center,
            iterations = 3
        )
    }
}

@Composable
fun HomeTopAppBar(
    category: String, score: Int, listSize: Int, navBack: () -> Unit
) {
    val fac = if (listSize == 10) 40.dp else 20.dp
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(top = 15.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                null,
                modifier = Modifier.clickable { navBack() })
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$category Quiz",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
        ) {
            when (score) {
                in 0..10 -> StretchableLine(score * fac)

            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$score",
                color = trueAnswer,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "/$listSize",
                color = falseAnswer,
                fontSize = 18.sp,
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    QuizMasterTheme {
        val quizViewModel: QuizViewModel = viewModel()
        val gameUiState by quizViewModel.gameUiState.collectAsState()
        GameLayout(question = " hello ali",
            quizViewModel = quizViewModel,
            gameUiState = gameUiState,
            listAnswer = gameUiState.listOfAnswer,
            correctAnswer = gameUiState.correctAnswer,
            clicked = gameUiState.clicked,
            score = gameUiState.score,
            counter = gameUiState.counter,
            listSize = 10,
            onNextClick = {},
            {})

    }
}

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

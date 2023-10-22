package com.example.quizzard.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizzard.QuizUiState
import com.example.quizzard.QuizViewModel
import com.example.quizzard.R
import com.example.quizzard.data.GameUiState
import com.example.quizzard.data.Question
import com.example.quizzard.ui.theme.QuizzardTheme

@Composable
fun HomeScreen(
    quizViewModel : QuizViewModel,
    navToFinalScreen : () ->Unit
) {

    when(val quizUiState = quizViewModel.quizUiState){
        is QuizUiState.Success -> GameScreen(quizUiState.question.results, quizViewModel, navToFinalScreen)
        is QuizUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(
    questionList: List<Question>,
   quizViewModel : QuizViewModel,
    navToFinalScreen : () ->Unit
){
    quizViewModel.getQuestionDetails(questionList)
    val gameUiState by quizViewModel.gameUiState.collectAsState()

    Column (
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        GameLayout(
            question = gameUiState.question,
            quizViewModel = quizViewModel,
            gameUiState = gameUiState,
            listAnswer = gameUiState.listOfAnswer,
            correctAnswer = gameUiState.correctAnswer,
            clicked = gameUiState.clicked,
            score = gameUiState.score,
            counter = gameUiState.counter,
            onNextClick = { quizViewModel.onNextClick(navToFinalScreen) }
        )
    }

}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout(
    question : String,
    quizViewModel: QuizViewModel,
    gameUiState: GameUiState,
    listAnswer: List<Any>,
    correctAnswer: String,
    clicked: Boolean,
    score : Int,
    counter : Int,
    onNextClick: () -> Unit
) {
    val shuffledListAnswer = remember(listAnswer) { listAnswer.shuffled() }
    Scaffold(
        topBar = { HomeTopAppBar(gameUiState.category, score) }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            QuestionCard(question, counter)
            Spacer(modifier = Modifier.height(25.dp))

            AnswersList(
                listAnswer = shuffledListAnswer,
                correctAnswer = correctAnswer,
                clicked = clicked,
                onItemClicked = { quizViewModel.onItemClicked() },
                incScore = { quizViewModel.incScore() }
            )

            Button(onClick = { onNextClick() })
            {
                Text(text = "Next")
            }

//            Text(
//                text = " SCORE : $score",
//            )
        }
    }
}

@Composable
fun QuestionCard(
    question : String,
    counter : Int
){
    Card(
        modifier = Modifier
            .size(350.dp, 200.dp),
        colors = CardDefaults.cardColors(Color.Unspecified),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(Color(0xFFF6D1AB))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = "${counter +1}/10",
                fontSize = 20.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.End
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                text = question,
                textAlign = TextAlign.Start,
                color = Color(0xff642900),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AnswerItem(
    listAnswer: List<Any>,
    correctAnswer : String,
    possibleAnswer: Any,
    clicked : Boolean,
    onItemClicked : () ->Unit,
    incScore : () ->Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onItemClicked()
                if (possibleAnswer == correctAnswer) {
                    incScore()
                }
            },
        colors =
        if(clicked){
            if (possibleAnswer == correctAnswer) {
                CardDefaults.cardColors(Color(0xFF2E996D))
        }   else {
                CardDefaults.cardColors(Color(0xFFEB8844))
            }
        } else CardDefaults.cardColors(Color.Unspecified),
        border = BorderStroke(1.dp, Color.LightGray),
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${(listAnswer.indexOf(possibleAnswer) + 1)}.",
                Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = possibleAnswer.toString(),
                Modifier.fillMaxWidth(),
            )
        }
    }
}
@Composable
fun AnswersList(
    listAnswer: List<Any>,
    correctAnswer :String,
    clicked :Boolean,
    onItemClicked : ()-> Unit,
    incScore : ()->Unit
,){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ){
        items(listAnswer){possibleAnswer ->

            AnswerItem(
                listAnswer = listAnswer,
                correctAnswer =correctAnswer ,
                possibleAnswer = possibleAnswer,
                clicked = clicked,
                onItemClicked = { onItemClicked() },
                incScore = incScore
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(id = R.string.loading)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(category : String = "Math", score : Int){
    CenterAlignedTopAppBar(
        title = {
            Column{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$category Quiz",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xff642900)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    when(score){
                        1 -> StretchableLine(40.dp)
                        2 -> StretchableLine(80.dp)
                        3 -> StretchableLine(120.dp)
                        4 -> StretchableLine(160.dp)
                        5 -> StretchableLine(200.dp)
                        6 -> StretchableLine(240.dp)
                        7 -> StretchableLine(280.dp)
                        8 -> StretchableLine(320.dp)
                        9 -> StretchableLine(360.dp)
                        10-> StretchableLine(400.dp)
                    }
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "$score",
                        color = Color(0xFF2E996D),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "/10",
                        color = Color(0xFFEB8844),
                        fontSize = 18.sp,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Unspecified)
    )
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview(){
    QuizzardTheme {
        val quizViewModel : QuizViewModel = viewModel()
        val gameUiState by quizViewModel.gameUiState.collectAsState()
        GameLayout(
            question = gameUiState.question,
            quizViewModel = quizViewModel,
            gameUiState = gameUiState,
            listAnswer = gameUiState.listOfAnswer,
            correctAnswer = gameUiState.correctAnswer,
            clicked = gameUiState.clicked,
            score = gameUiState.score,
            counter = gameUiState.counter,
            onNextClick = {}
        )

    }
}

@Composable
fun StretchableLine(
    width: Dp = 40.dp,
    color: Color = Color(0xFF2E996D)
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color = Color(0x80F6D1AB), RoundedCornerShape(10.dp))
    ){
        Box(
            modifier = Modifier
                .width(width)
                .height(10.dp)
                .background(color, RoundedCornerShape(10.dp))
        )
    }
}

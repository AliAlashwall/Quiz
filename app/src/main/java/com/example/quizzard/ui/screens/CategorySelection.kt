package com.example.quizzard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizzard.QuizViewModel
import com.example.quizzard.R
import com.example.quizzard.ui.theme.QuizMasterTheme

@Composable
fun CategorySelection(
    gameViewModel: QuizViewModel,
    navToHome : ()->Unit
) {
val gameUiState by gameViewModel.gameUiState.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        QuizTopAppBar(name =gameUiState.userName)

        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            text = "👉 Practice more",
            fontWeight = FontWeight.Bold

        )

        DailyQuizItem {
            gameViewModel.onDailyQuizClicked { navToHome() }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            text = "👉 Specific category",
            fontWeight = FontWeight.Bold
            )
        CategoryItem(
            category = "Sports",
            classNum = "A",
            onCategoryClicked = { gameViewModel.onSportClicked { navToHome() } },
            navToHome = {navToHome()}
        )
        CategoryItem(
            category = "Programing",
            classNum = "B",
            onCategoryClicked = { gameViewModel.onComputerClicked{ navToHome() } },
            navToHome = {navToHome()}
        )
        CategoryItem(
            category = "Math",
            classNum = "C",
            onCategoryClicked = { gameViewModel.onMathClicked { navToHome() } },
            navToHome = {navToHome()}
        )
        CategoryItem(
            category = "History",
            classNum = "D",
            onCategoryClicked = { gameViewModel.onHistoryClicked{ navToHome() } },
            navToHome = {navToHome()}
        )
    }
}

@Composable
fun DailyQuizItem(
    onDailyQuizClicked : ()->Unit
){
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onDailyQuizClicked() },
        colors = CardDefaults.cardColors(Color(0xFF2E996D))
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(
                modifier = Modifier.padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Daily Quiz",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text ="20 Mixed questions",
                    Modifier.padding(start = 4.dp),
                    color = Color.White,
                )
            }
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription =null,
                tint = Color.White
            )
        }
    }
}
@Composable
fun QuizTopAppBar(name :String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp),
                text = "👋 Hi $name ,",
                textAlign = TextAlign.Start,
                color = Color(0xff642900),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "Great to see you !",
                textAlign = TextAlign.Start,
                color = Color(0xff642900),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.padding(20.dp),
            painter = painterResource(id = R.drawable.person),
            contentDescription = null
        )
    }
}
@Composable
fun CategoryItem(
    category : String,
    classNum : String,
    onCategoryClicked: (() -> Unit) -> Unit,
    navToHome: () -> Unit
){
    Card(
        modifier = Modifier
            .padding(20.dp, 10.dp)
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(70.dp)
            .clickable {
                onCategoryClicked { navToHome() }
            },
        colors = CardDefaults.cardColors(Color(0xFFF6D1AB))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Card(
                modifier = Modifier.size(50.dp),
                colors = CardDefaults.cardColors(Color(0xFFFAEAD9))
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = classNum,
                        color = Color(0xFFE1543C),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Class",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Text(
                text = category,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff642900)
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription =null )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FirstPrev(){
    QuizMasterTheme {
    val gameViewModel: QuizViewModel = viewModel()
    CategorySelection(gameViewModel){}
    }
}
package com.example.quizzard.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizzard.QuizViewModel

enum class QuizScreen {
    Start,
    CategorySelection,
    Home,
    Final
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizScreen(navController: NavHostController = rememberNavController()){

    val quizViewModel: QuizViewModel = viewModel()
    val gameUiState by quizViewModel.gameUiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = QuizScreen.Start.name,

    ){
        composable(route = QuizScreen.Start.name){
            StartScreen(quizViewModel) {
                navController.navigate(QuizScreen.CategorySelection.name)
            }
        }
        composable(route = QuizScreen.CategorySelection.name){
             CategorySelection(quizViewModel){
                navController.navigate(QuizScreen.Home.name)}
        }
        composable(route = QuizScreen.Home.name){
            HomeScreen(quizViewModel,
                { navController.navigate(QuizScreen.Final.name) },
                {quizViewModel.backToHome{ navController.popBackStack(QuizScreen.CategorySelection.name, false)} }
            )

        }
        composable(route = QuizScreen.Final.name){
            FinalScreen(
                userName =gameUiState.userName,
                score = gameUiState.score,
                onTryAgainClicked = {navController.popBackStack(QuizScreen.Start.name, false)},
                listSize = gameUiState.questionListSize,
                resetQuiz = {quizViewModel.resetGame()}
            )
        }
    }

}
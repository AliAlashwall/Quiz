package com.example.quizzard.presentation.screens

data class GameUiState(
    var userName : String = "",
    var clicked : Boolean = false,
    var score : Int = 0,
    var counter : Int = 0,
    var correctAnswer : String = "",
    var question: String = "",
    var listOfAnswer : List<String> = listOf(""),
    var endQuiz : Boolean = false,
    var category : String = "",
    var questionListSize : Int = 10,

)

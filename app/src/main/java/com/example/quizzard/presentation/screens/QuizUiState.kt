package com.example.quizzard.presentation.screens

import com.example.quizzard.domain.model.QuizData

sealed class QuizUiState {
    data class Success(val question: QuizData) : QuizUiState()
    object Loading : QuizUiState()
    data class Error(val message: String) : QuizUiState()

}
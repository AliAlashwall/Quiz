package com.example.quizzard.presentation.screens.models

import com.example.quizzard.domain.model.QuizData

sealed class QuizDataState {
    data class Success(val question: QuizData) : QuizDataState()
    object Loading : QuizDataState()
    data class Error(val message: String) : QuizDataState()

}
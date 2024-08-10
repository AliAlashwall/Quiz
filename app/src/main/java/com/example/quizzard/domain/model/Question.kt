package com.example.quizzard.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizData(
    @SerialName(value = "response_code")
    val responseCode: Int,
    val results: List<Question>
)
@Serializable
data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerialName(value = "correct_answer")
    val correctAnswer: String,
    @SerialName(value = "incorrect_answers")
    val incorrectAnswers: List<String>,
)

package com.example.quizzard.domain.repository

import com.example.quizzard.domain.model.QuizData

interface RemoteQuizRepository {
    suspend fun getComputerQuestions() : QuizData
    suspend fun getSportsQuestion() : QuizData
    suspend fun getMathQuestions() : QuizData
    suspend fun getHistoryQuestions() : QuizData
    suspend fun getDailyQuiz() : QuizData
}
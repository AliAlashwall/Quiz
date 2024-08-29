package com.example.quizzard.domain.repository

import com.example.quizzard.domain.model.QuizData
import retrofit2.Response

interface RemoteQuizRepository {
    suspend fun getQuizQuestions(amount: Int, category: Int): Response<QuizData>
}
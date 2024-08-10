package com.example.quizzard.data.data_source

import com.example.quizzard.domain.model.QuizData
import retrofit2.http.GET


interface QuizApiService {
    @GET("api.php?amount=10&category=18")
    suspend fun getComputerQuestions(): QuizData

    @GET("api.php?amount=10&category=21")
    suspend fun getSportsQuestion(): QuizData

    @GET("api.php?amount=10&category=19&difficulty=medium")
    suspend fun getMathQuestions(): QuizData

    @GET("api.php?amount=10&category=23")
    suspend fun getHistoryQuestions(): QuizData

    @GET("api.php?amount=20")
    suspend fun getDailyQuiz(): QuizData

}

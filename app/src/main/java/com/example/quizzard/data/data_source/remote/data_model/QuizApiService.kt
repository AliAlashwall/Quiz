package com.example.quizzard.data.data_source.remote.data_model

import com.example.quizzard.domain.model.QuizData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuizApiService {
//    @GET
//    suspend fun getComputerQuestions(
//        @Query("amount") amount: Int = 10,
//        @Query("category") category: Int
//    ): Response<QuizData>
//
//    @GET("api.php?amount=10&category=21")
//    suspend fun getSportsQuestion(): Response<QuizData>
//
//    @GET("api.php?amount=10&category=19&difficulty=medium")
//    suspend fun getMathQuestions(): Response<QuizData>
//
//    @GET("api.php?amount=10&category=23")
//    suspend fun getHistoryQuestions(): Response<QuizData>
//
//    @GET("api.php?amount=20")
//    suspend fun getDailyQuiz(): Response<QuizData>

    @GET
    suspend fun getQuizQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int? ,
    ): Response<QuizData>
}

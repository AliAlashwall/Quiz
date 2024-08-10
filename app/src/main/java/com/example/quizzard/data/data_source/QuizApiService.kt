package com.example.quizzard.data.data_source

import com.example.quizzard.domain.model.QuizData
import com.example.quizzard.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(Constants.BASE_URL)
    .build()

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

object QuizApi {
    val retrofitService: QuizApiService by lazy {
        retrofit.create(QuizApiService::class.java)
    }
}
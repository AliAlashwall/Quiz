package com.example.quizzard.network

import com.example.quizzard.data.QuizData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://opentdb.com/"

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(loggingInterceptor)
        connectTimeout(20, TimeUnit.MINUTES) // connect timeout
        readTimeout(30, TimeUnit.MINUTES) // socket timeout
        writeTimeout(20, TimeUnit.MINUTES) // write timeout
    }.build()
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .client(provideOkHttpClient())
    .baseUrl(BASE_URL)
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
package com.example.quizzard.data.data_source.remote.data_model

import com.example.quizzard.domain.model.QuizData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuizApiService {

    @GET("api.php")
    suspend fun getQuizQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int? ,
    ): Response<QuizData>
}

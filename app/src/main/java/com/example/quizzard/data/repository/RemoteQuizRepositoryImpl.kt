package com.example.quizzard.data.repository

import com.example.quizzard.data.data_source.remote.data_model.QuizApiService
import com.example.quizzard.domain.model.QuizData
import com.example.quizzard.domain.repository.RemoteQuizRepository
import retrofit2.Response

class RemoteQuizRepositoryImpl(private val quizApiService: QuizApiService) : RemoteQuizRepository {

    override suspend fun getQuizQuestions(amount: Int, category: Int): Response<QuizData> {
        return quizApiService.getQuizQuestions(amount, category)
    }
}




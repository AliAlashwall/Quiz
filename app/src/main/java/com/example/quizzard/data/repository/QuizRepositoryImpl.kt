package com.example.quizzard.data.repository

import com.example.quizzard.domain.model.QuizData
import com.example.quizzard.domain.repository.QuizRepository
import com.example.quizzard.data.data_source.remote.data_model.QuizApiService

class QuizRepositoryImpl(private val quizApiService: QuizApiService) : QuizRepository {
    override suspend fun getComputerQuestions(): QuizData {
        return quizApiService.getComputerQuestions()
    }

    override suspend fun getSportsQuestion(): QuizData {
        return quizApiService.getSportsQuestion()
    }

    override suspend fun getMathQuestions(): QuizData {
        return quizApiService.getMathQuestions()
    }

    override suspend fun getHistoryQuestions(): QuizData {
        return quizApiService.getHistoryQuestions()
    }

    override suspend fun getDailyQuiz(): QuizData {
        return quizApiService.getDailyQuiz()
    }
}
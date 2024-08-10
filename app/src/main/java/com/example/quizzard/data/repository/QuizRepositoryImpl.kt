package com.example.quizzard.data.repository

import com.example.quizzard.domain.model.QuizData
import com.example.quizzard.domain.repository.QuizRepository
import com.example.quizzard.data.data_source.QuizApi

class QuizRepositoryImpl : QuizRepository {
    override suspend fun getComputerQuestions(): QuizData {
        return QuizApi.retrofitService.getComputerQuestions()
    }
    override suspend fun getSportsQuestion(): QuizData {
        return QuizApi.retrofitService.getSportsQuestion()
    }
    override suspend fun getMathQuestions(): QuizData {
        return QuizApi.retrofitService.getMathQuestions()
    }
    override suspend fun getHistoryQuestions(): QuizData {
        return QuizApi.retrofitService.getHistoryQuestions()
    }
    override suspend fun getDailyQuiz(): QuizData {
        return QuizApi.retrofitService.getDailyQuiz()
    }
}
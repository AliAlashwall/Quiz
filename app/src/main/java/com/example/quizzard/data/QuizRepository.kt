package com.example.quizzard.data

import com.example.quizzard.network.QuizApi

interface QuizRepository {
    suspend fun getComputerQuestions() : QuizData
    suspend fun getSportsQuestion() : QuizData
    suspend fun getMathQuestions() : QuizData
    suspend fun getHistoryQuestions() : QuizData
    suspend fun getDailyQuiz() : QuizData
}

class QuizRepositoryImpl : QuizRepository{
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
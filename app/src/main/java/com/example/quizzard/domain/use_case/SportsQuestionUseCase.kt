package com.example.quizzard.domain.use_case

import com.example.quizzard.domain.repository.QuizRepository

class SportsQuestionUseCase
    (private val quizRepository: QuizRepository)
{
    suspend operator fun invoke() = quizRepository.getSportsQuestion()
}
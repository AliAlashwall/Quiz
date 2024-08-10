package com.example.quizzard.domain.use_case

import com.example.quizzard.domain.repository.QuizRepository

class ComputerQuestionsUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke() = quizRepository.getComputerQuestions()
}
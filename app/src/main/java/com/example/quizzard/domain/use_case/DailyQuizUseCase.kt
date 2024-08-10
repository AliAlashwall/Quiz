package com.example.quizzard.domain.use_case

import com.example.quizzard.domain.repository.QuizRepository

class DailyQuizUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke() = quizRepository.getDailyQuiz()
}
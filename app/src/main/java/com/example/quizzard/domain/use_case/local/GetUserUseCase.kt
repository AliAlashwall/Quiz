package com.example.quizzard.domain.use_case.local

import com.example.quizzard.domain.repository.LocalQuizRepository

class GetUserUseCase(private val localQuizRepository: LocalQuizRepository) {
    suspend operator fun invoke() = localQuizRepository.getUserHistory()
}
package com.example.quizzard.domain.use_case.local

import com.example.quizzard.domain.model.User
import com.example.quizzard.domain.repository.LocalQuizRepository

class InsertUserUseCase(private val localQuizRepository: LocalQuizRepository) {
    suspend operator fun invoke(user: User) = localQuizRepository.insertUser(user)
}
package com.example.quizzard.domain.use_case.remote

import com.example.quizzard.domain.repository.RemoteQuizRepository

class ComputerQuestionsUseCase(
    private val remoteQuizRepository: RemoteQuizRepository
) {
    suspend operator fun invoke() = remoteQuizRepository.getComputerQuestions()
}
package com.example.quizzard.domain.use_case.remote

import com.example.quizzard.domain.repository.RemoteQuizRepository
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val remoteQuizRepository: RemoteQuizRepository
) {
    suspend operator fun invoke(amount: Int, category: Int) =
        remoteQuizRepository.getQuizQuestions(amount, category)
}

/*
*     val mathQuestionsUseCase: MathQuestionsUseCase,
    val computerQuestionsUseCase: ComputerQuestionsUseCase,
    val historyQuestionsUseCase: HistoryQuestionsUseCase,
    val sportsQuestionUseCase: SportsQuestionUseCase,
    val dailyQuizUseCase: DailyQuizUseCase*/
package com.example.quizzard.domain.use_case

data class QuizUseCase(
    val mathQuestionsUseCase: MathQuestionsUseCase,
    val computerQuestionsUseCase: ComputerQuestionsUseCase,
    val historyQuestionsUseCase: HistoryQuestionsUseCase,
    val sportsQuestionUseCase: SportsQuestionUseCase,
    val dailyQuizUseCase: DailyQuizUseCase
)

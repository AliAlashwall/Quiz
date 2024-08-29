package com.example.quizzard.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzard.domain.model.Question
import com.example.quizzard.domain.use_case.remote.GetQuizQuestionsUseCase
import com.example.quizzard.presentation.screens.models.QuizUiState
import com.example.quizzard.presentation.screens.models.QuizDataState
import com.example.quizzard.presentation.screens.models.Subject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.jsoup.Jsoup
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: GetQuizQuestionsUseCase
) : ViewModel() {

    var quizDataState: QuizDataState by mutableStateOf(QuizDataState.Loading)
        private set

    private val _quizUiState = MutableStateFlow(QuizUiState())
    val quizUiState: StateFlow<QuizUiState> = _quizUiState

    private suspend fun handleResponse(amount: Int, category: Int): QuizDataState {
        return quizUseCase(amount = amount, category = category).let {
            if (it.isSuccessful) {
                QuizDataState.Success(it.body()!!)
            } else {
                QuizDataState.Error(it.errorBody().toString())
            }
        }
    }

    private fun getQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            val category = _quizUiState.value.category
            quizDataState = try {
                when (category) {
                    Subject.Daily -> handleResponse(amount = 20, category = 0)
                    Subject.Programing -> handleResponse(amount = 10, category = 10)
                    Subject.Math -> handleResponse(amount = 10, category = 19)
                    Subject.Sports -> handleResponse(amount = 10, category = 21)
                    Subject.History -> handleResponse(amount = 10, category = 23)
                    else -> QuizDataState.Loading
                }
            } catch (e: IOException) {
                QuizDataState.Error("Network Error")
            }
        }
    }


    fun onSubjectClicked(category: Subject) {
        _quizUiState.update {
            it.copy(
                category = category
            )
        }
        getQuestion()
    }

    fun updateUserName(name: String) {
        _quizUiState.update {
            it.copy(
                userName = name
            )
        }
    }

    fun getQuestionDetails(questionsList: List<Question>) {
        val question = questionsList[_quizUiState.value.counter]
        _quizUiState.update {
            it.copy(
                question = Jsoup.parse(question.question).text(),
                listOfAnswer = (question.incorrectAnswers + question.correctAnswer).map { answer ->
                    Jsoup.parse(
                        answer
                    ).text()
                },
                correctAnswer = Jsoup.parse(question.correctAnswer).text(),
                questionListSize = questionsList.size
            )
        }
    }

    fun onNextClick(navToEnd: () -> Unit) {
        var incCounter = _quizUiState.value.counter.plus(1)
        val listSize = _quizUiState.value.questionListSize
        if (incCounter == listSize) {
            incCounter = (listSize - 1)
        }
        val endGame = _quizUiState.value.counter == listSize - 1
        _quizUiState.update {
            it.copy(
                clicked = false,
                counter = incCounter,
                endQuiz = endGame
            )
        }
        if (endGame) {
            navToEnd()
        }
    }

    fun backToHome() {
        _quizUiState.update {
            it.copy(
                clicked = false,
                score = 0,
                counter = 0,
                correctAnswer = "",
                question = "",
                listOfAnswer = listOf(""),
                endQuiz = false,
            )
        }
    }

    fun onItemClicked(index: Int) {
        _quizUiState.update {
            it.copy(
                clicked = true,
                itemIndexed = index
            )
        }
    }

    fun incScore() {
        val newScore = _quizUiState.value.score.plus(1)
        _quizUiState.update {
            it.copy(
                score = newScore
            )
        }
    }

    fun resetGame() {
        _quizUiState.update {
            it.copy(
                userName = "",
                clicked = false,
                score = 0,
                counter = 0,
                correctAnswer = "",
                question = "",
                listOfAnswer = listOf(""),
                endQuiz = false,
                category = Subject.Empty,
            )
        }
    }
}


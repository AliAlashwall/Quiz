package com.example.quizzard.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzard.domain.model.Question
import com.example.quizzard.domain.model.QuizData
import com.example.quizzard.domain.use_case.remote.GetQuizQuestionsUseCase
import com.example.quizzard.presentation.screens.models.GameUiState
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

interface QuizUiState {
    data class Success(val question: QuizData) : QuizUiState
    object Loading : QuizUiState
    data class Error(val message: String) : QuizUiState

}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: GetQuizQuestionsUseCase
) : ViewModel() {

    var quizUiState: QuizUiState by mutableStateOf(QuizUiState.Loading)
        private set

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState.asStateFlow()

    private suspend fun handleResponse(amount: Int, category: Int): QuizUiState {
        return quizUseCase(amount = amount, category = category).let {
            if (it.isSuccessful) {
                QuizUiState.Success(it.body()!!)
            } else {
                QuizUiState.Error(it.errorBody().toString())
            }
        }
    }

    private fun getQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            val category = _gameUiState.value.category
            quizUiState = try {
                when (category) {
                    Subject.Daily -> handleResponse(amount = 20, category = 0)
                    Subject.Programing -> handleResponse(amount = 10, category = 10)
                    Subject.Math -> handleResponse(amount = 10, category = 19)
                    Subject.Sports -> handleResponse(amount = 10, category = 21)
                    Subject.History -> handleResponse(amount = 10, category = 23)
                    else -> QuizUiState.Loading
                }
            } catch (e: IOException) {
                QuizUiState.Error("Network Error")
            }
        }
    }


    fun onSubjectClicked(category: Subject) {
        _gameUiState.update {
            it.copy(
                category = category
            )
        }
        getQuestion()
    }

    fun updateUserName(name: String) {
        _gameUiState.update {
            it.copy(
                userName = name
            )
        }
    }

    fun getQuestionDetails(questionsList: List<Question>) {
        val question = questionsList[_gameUiState.value.counter]
        _gameUiState.update {
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
        var incCounter = _gameUiState.value.counter.plus(1)
        val listSize = _gameUiState.value.questionListSize
        if (incCounter == listSize) {
            incCounter = (listSize - 1)
        }
        val endGame = _gameUiState.value.counter == listSize - 1
        _gameUiState.update {
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
        _gameUiState.update {
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
        _gameUiState.update {
            it.copy(
                clicked = true,
                itemIndexed = index
            )
        }
    }

    fun incScore() {
        val newScore = _gameUiState.value.score.plus(1)
        _gameUiState.update {
            it.copy(
                score = newScore
            )
        }
    }

    fun resetGame() {
        _gameUiState.update {
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


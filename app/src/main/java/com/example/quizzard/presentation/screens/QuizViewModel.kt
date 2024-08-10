package com.example.quizzard

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzard.data.GameUiState
import com.example.quizzard.data.NetworkQuizRepository
import com.example.quizzard.data.Question
import com.example.quizzard.data.QuizData
import kotlinx.coroutines.launch
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
    object Error : QuizUiState

}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase
) : ViewModel() {

    private fun getQuestion() {
        viewModelScope.launch {

            val category = _gameUiState.value.category
            quizUiState = try {
                when (category) {
                    Subject.Math -> QuizUiState.Success(QuizApi.retrofitService.getMathQuestions())
                    Subject.Daily -> QuizUiState.Success(QuizApi.retrofitService.getDailyQuiz())
                    Subject.Programing -> QuizUiState.Success(QuizApi.retrofitService.getComputerQuestions())
                    Subject.Sports -> QuizUiState.Success(QuizApi.retrofitService.getSportsQuestion())
                    Subject.History -> QuizUiState.Success(QuizApi.retrofitService.getHistoryQuestions())
                    else -> QuizUiState.Loading
                }
            } catch (e: IOException) {
                QuizUiState.Error
            }
        }
    }

    var quizUiState: QuizUiState by mutableStateOf(QuizUiState.Loading)
        private set

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState.asStateFlow()

    fun onSportClicked(navToHome: () -> Unit) {
        _gameUiState.update {
            it.copy(
                category = Subject.Sports
            )
        }
        getQuestion()
        navToHome()
    }

    fun onComputerClicked(navToHome: () -> Unit) {
        _gameUiState.update {
            it.copy(
                category = Subject.Programing
            )
        }
        getQuestion()
        navToHome()
    }

    fun onMathClicked(navToHome: () -> Unit) {
        _gameUiState.update {
            it.copy(
                category = Subject.Math
            )
        }
        getQuestion()
        navToHome()
    }

    fun onDailyQuizClicked(navToHome: () -> Unit) {
        viewModelScope.launch {
            _gameUiState.update {
                it.copy(
                    category = Subject.Daily
                )
            }
            getQuestion()
            navToHome()
        }
    }

    fun onHistoryClicked(navToHome: () -> Unit) {
        viewModelScope.launch {
            _gameUiState.update {
                it.copy(
                    category = Subject.History
                )
            }
            getQuestion()
            navToHome()
        }
    }

    fun updateUserName(name: String) {
        _gameUiState.update {
            it.copy(
                userName = name
            )
        }
    }

    @SuppressLint("SuspiciousIndentation")
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

    fun backToHome(navToHome: () -> Unit) {
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
        navToHome()
    }

    fun onItemClicked() {
        _gameUiState.update {
            it.copy(
                clicked = true
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


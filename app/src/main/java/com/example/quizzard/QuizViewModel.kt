package com.example.quizzard

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzard.data.GameUiState
import com.example.quizzard.data.Question
import com.example.quizzard.data.QuizData
import kotlinx.coroutines.launch
import com.example.quizzard.network.QuizApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jsoup.Jsoup

interface QuizUiState{
    object Loading : QuizUiState
    data class Success(val question: QuizData) : QuizUiState
//    object Error
}

class QuizViewModel :ViewModel() {
    var quizUiState: QuizUiState by mutableStateOf(QuizUiState.Loading)
        private set

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState : StateFlow<GameUiState> = _gameUiState.asStateFlow()

    init {
        getQuestion()
    }
    fun onSportClicked(navToHome :()->Unit) {
        _gameUiState.update {
            it.copy(
                category = "Sports"
            )
        }
        navToHome()
    }
    fun onComputerClicked(navToHome :()->Unit) {
        _gameUiState.update {
            it.copy(
                category = "Computer Science"
            )
        }
        navToHome()
    }

    fun onMathClicked(navToHome :()->Unit) {
        _gameUiState.update {
            it.copy(
                category = "Math"
            )
        }
        navToHome()
    }
    fun getQuestion() {
        viewModelScope.launch {
            val category = _gameUiState.value.category

            quizUiState = when (category) {
                "Sports" -> QuizUiState.Success(QuizApi.retrofitService.getSportsQuestion())
                "Computer Science" -> QuizUiState.Success(QuizApi.retrofitService.getComputerQuestions())
                 else-> QuizUiState.Success(QuizApi.retrofitService.getMathQuestions())

            }

        }
    }
//-----------------//-----------------//-----------------//-----------------//-----------------//-----------------
fun updateUserName(name:String){
    _gameUiState.update {
        it.copy(
            userName = name
        )
    }
}
    @SuppressLint("SuspiciousIndentation")
    fun getQuestionDetails(questionsList : List<Question>){
        val question = questionsList[_gameUiState.value.counter]
            _gameUiState.update {
                it.copy(
                    question = Jsoup.parse(question.question).text(),
                    listOfAnswer = (question.incorrectAnswers + question.correctAnswer),
                    correctAnswer = question.correctAnswer
                )
            }
    }
    fun onNextClick(navToEnd : ()->Unit){
        var incCounter =_gameUiState.value.counter.plus(1)
        if (incCounter == 10){incCounter = 9}
        val endGame = _gameUiState.value.counter == 9
        _gameUiState.update {
            it.copy(
                clicked = false,
                counter = incCounter,
                endQuiz = endGame
            )
        }
        if (endGame){
            navToEnd()
        }
    }
    fun onItemClicked(){
        _gameUiState.update {
            it.copy(
                clicked = true
            )
        }
    }

    fun incScore(){
        val newScore = _gameUiState.value.score.plus(1)
        _gameUiState.update {
            it.copy(
                score = newScore
            )
        }
    }
}


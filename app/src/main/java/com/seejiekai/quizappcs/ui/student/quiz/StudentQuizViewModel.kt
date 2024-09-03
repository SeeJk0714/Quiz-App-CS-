package com.seejiekai.quizappcs.ui.student.quiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.model.Result
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.data.repo.ResultRepo
import com.seejiekai.quizappcs.data.repo.UserRepo
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentQuizViewModel @Inject constructor(
    private val authService: AuthService,
    private val quizRepo: QuizRepo,
    private val resultRepo: ResultRepo,
    private val state: SavedStateHandle

): BaseViewModel() {
    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz: StateFlow<Quiz?> = _quiz
    private val accessId = state.get<String>("accessId")
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    init {
        viewModelScope.launch {
            accessId?.let { id ->
                _quiz.value = quizRepo.verifyAccessCode(id)
            }
        }
    }

    private fun getStudentById(): String {
        return authService.getUid() ?: throw Exception("User not authenticated")
    }

    fun goToNextQuestion() {
        viewModelScope.launch { val currentIndex = _currentQuestionIndex.value
            Log.d("currentIndex", currentIndex.toString())
            if ((_quiz.value?.questions?.size ?: 0) > currentIndex + 1) {
                _currentQuestionIndex.value += 1
            }
        }
    }

    suspend fun addResult(quizId: String, mark: Int) {
        try {
            if (!resultRepo.checkTheResult(quizId, getStudentById())) {

                val result = Result(
                    finishId = quizId,
                    studentId = getStudentById(),
                    mark = mark
                )
                resultRepo.addResult(result)
                finish.emit(Unit)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
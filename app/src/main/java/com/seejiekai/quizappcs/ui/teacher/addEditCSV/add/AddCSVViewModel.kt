package com.seejiekai.quizappcs.ui.teacher.addEditCSV.add

import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.ui.teacher.addEditCSV.base.BaseManageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddCSVViewModel @Inject constructor(
    private val repo: QuizRepo,
    private val authService: AuthService
): BaseManageViewModel() {

    private fun randomCode(): String {
        // Generates a number between 100000 and 999999
        val random = Random.nextInt(100000, 1000000)
        return random.toString()
    }

    private fun generateQuizId(): String {
        return repo.getNewQuizById()
    }

    private fun getCreateById(): String {
        return authService.getUid() ?: throw Exception("User not authenticated")
    }

    fun uploadCSV(name: String,desc: String) {
        viewModelScope.launch {
            try {
                val quiz = Quiz(
                    quizId = generateQuizId(),
                    accessCode = randomCode(),
                    quizName = name,
                    description = desc,
                    questions = questions.value, // Get the current value of questions
                    createBy = getCreateById()
                )

                repo.addQuiz(quiz)
                finish.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
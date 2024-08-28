package com.seejiekai.quizappcs.ui.addEditCSV.add

import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddCSVViewModel @Inject constructor(
    private val repo: QuizRepo
): BaseManageViewModel() {

    private fun randomID(): String {
        // Generates a number between 100000 and 999999
        val random = Random.nextInt(100000, 1000000)
        return random.toString()
    }

    fun uploadCSV(name: String) {
        viewModelScope.launch {
            try {
                val quiz = Quiz(
                    quizId = randomID(),
                    quizName = name,
                    questions = questions.value // Get the current value of questions
                )
                repo.addQuiz(quiz)
                finish.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
package com.seejiekai.quizappcs.ui.addEditCSV.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCSVViewModel @Inject constructor(
    private val quizRepo: QuizRepo,
): BaseManageViewModel() {
    val quiz: MutableStateFlow<Quiz?> = MutableStateFlow(null)

    fun getQuizById(id: String) {
        viewModelScope.launch {
            quiz.value = quizRepo.getQuizById(id)
        }
    }

    fun uploadCSV(name: String) {
        quiz.value?.let {
            val newQuiz = it.copy(quizName = name)
            viewModelScope.launch {
                quizRepo.updateQuiz(newQuiz)
                finish.emit(Unit)
            }
        }
    }
}
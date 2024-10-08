package com.seejiekai.quizappcs.ui.teacher.addEditCSV.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.data.model.Question
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.ui.teacher.addEditCSV.base.BaseManageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCSVViewModel @Inject constructor(
    private val quizRepo: QuizRepo,
    private val state: SavedStateHandle

): BaseManageViewModel() {
    val quiz: MutableStateFlow<Quiz?> = MutableStateFlow(null)
    private val quizId = state.get<String>("quizId")

    init {
        viewModelScope.launch {
            quizId?.let { id ->
                quiz.value = quizRepo.getQuizById(id)
            }
        }
    }

    fun uploadCSV(name: String, desc: String) {

        quiz.value?.let {
            val newQuiz = it.copy(quizName = name, description = desc, questions = _questions.value)
            viewModelScope.launch {
                quizRepo.updateQuiz(newQuiz)
                finish.emit(Unit)
            }
        }
    }
}
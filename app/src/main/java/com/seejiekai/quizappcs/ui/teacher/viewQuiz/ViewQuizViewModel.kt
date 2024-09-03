package com.seejiekai.quizappcs.ui.teacher.viewQuiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.data.model.Question
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewQuizViewModel @Inject constructor(
    private val quizRepo: QuizRepo,
    private val state: SavedStateHandle
): BaseViewModel() {
    val quiz: MutableStateFlow<Quiz?> = MutableStateFlow(null)
    private val quizId = state.get<String>("quizId")

    init {
        viewModelScope.launch {
            quizId?.let { id ->
                errorHandler {
                    quiz.value = quizRepo.getQuizById(id)
                }
            }
        }
    }

}
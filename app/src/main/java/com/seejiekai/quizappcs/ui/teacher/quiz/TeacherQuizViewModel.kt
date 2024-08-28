package com.seejiekai.quizappcs.ui.teacher.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.model.Question
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.data.model.User
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.data.repo.UserRepo
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherQuizViewModel @Inject constructor(
    private val quizRepo: QuizRepo,
    private val authService: AuthService
): BaseViewModel() {
    fun getAllQuiz() = quizRepo.getAllQuizzes()
}
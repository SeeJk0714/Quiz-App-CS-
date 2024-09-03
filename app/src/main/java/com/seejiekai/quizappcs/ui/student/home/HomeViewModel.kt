package com.seejiekai.quizappcs.ui.student.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.service.AuthService
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
class HomeViewModel @Inject constructor(
    private val quizRepo: QuizRepo,
    private val resultRepo: ResultRepo,
    private val userRepo: UserRepo,
    private val authService: AuthService
): BaseViewModel() {
    val quizExists = MutableStateFlow<Boolean?>(null)
    val username = MutableStateFlow<String?>(null)

    private fun getStudentById(): String {
        return authService.getUid() ?: throw Exception("User not authenticated")
    }

    suspend fun checkTheResult(accessCode: String): Boolean {
        return resultRepo.checkTheResult(accessCode,getStudentById())
    }

    fun verifyAccessCode(accessCode: String) {
        viewModelScope.launch {
            val pinCode = quizRepo.verifyAccessCode(accessCode)
            quizExists.value = pinCode != null
        }
    }

    fun logout() {
        viewModelScope.launch {
            authService.logout()
        }
    }
}
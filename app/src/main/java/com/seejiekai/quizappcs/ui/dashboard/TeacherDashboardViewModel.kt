package com.seejiekai.quizappcs.ui.dashboard

import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherDashboardViewModel @Inject constructor(
    private val authService: AuthService
): BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            authService.logout()
        }
    }
}
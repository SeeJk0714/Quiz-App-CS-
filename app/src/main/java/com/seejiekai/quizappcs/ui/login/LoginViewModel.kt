package com.seejiekai.quizappcs.ui.login

import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.repo.UserRepo
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val authService: AuthService
): BaseViewModel() {
    val success: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                validate(email, pass)
                authService.loginWithEmailAndPass(email, pass)
            }?.let {
                success.emit(Unit)
            }
        }
    }

    private fun validate(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            throw Exception("Email or password cannot be empty")
        }
    }
}
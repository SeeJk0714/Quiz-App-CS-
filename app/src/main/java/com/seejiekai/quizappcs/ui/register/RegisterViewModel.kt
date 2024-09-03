package com.seejiekai.quizappcs.ui.register

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.core.utils.UserRoles
import com.seejiekai.quizappcs.core.utils.ValidationUtil
import com.seejiekai.quizappcs.data.model.User
import com.seejiekai.quizappcs.data.model.ValidationField
import com.seejiekai.quizappcs.data.repo.UserRepo
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

    fun createUser(userName: String, email: String, pass: String, confirmPass: String, role: String) {
        val error = ValidationUtil.validate(
            ValidationField(userName, "[a-zA-z ]{2,20}", "Enter a valid name"),
            ValidationField(email, "[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+", "Enter a valid email"),
            ValidationField(pass, "[a-zA-z0-9#$%]{3,20}", "Enter a valid password"),
            ValidationField(confirmPass, "[a-zA-z0-9#$%]{3,20}", "Enter a valid confirm password"),
            ValidationField(role, "STUDENT|TEACHER", "Enter a valid role")
        )

        //This converts the string "STUDENT" or "TEACHER" into the corresponding UserRoles enum constant.
        //For instance, if role is "STUDENT", UserRoles.valueOf(role) will return UserRoles.STUDENT.
        val userRole = UserRoles.valueOf(role)

        if (error == null){
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler {
                    authService.createUserWithEmailAndPass(email, pass)
                }?.let {
                    Log.d("createuser", userRole.toString())
                    userRepo.createUser(
                        User(
                            userName,
                            email,
                            userRole
                        )
                    )
                    success.emit(userRole)
                }
            }
        } else {
            viewModelScope.launch {
                _error.emit(error)
            }
        }
    }
}
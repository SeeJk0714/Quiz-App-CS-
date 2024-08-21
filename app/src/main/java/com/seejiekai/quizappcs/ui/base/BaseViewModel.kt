package com.seejiekai.quizappcs.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seejiekai.quizappcs.core.utils.UserRoles
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    val success: MutableSharedFlow<UserRoles> = MutableSharedFlow()

    suspend fun <T>errorHandler(func: suspend() -> T?): T? {
        return try {
            func()
        } catch (e: Exception) {
            _error.emit(e.message.toString())
            e.printStackTrace()
            null
        }
    }
}
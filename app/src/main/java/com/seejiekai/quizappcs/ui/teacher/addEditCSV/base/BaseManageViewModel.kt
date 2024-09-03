package com.seejiekai.quizappcs.ui.teacher.addEditCSV.base

import com.seejiekai.quizappcs.data.model.Question
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseManageViewModel:BaseViewModel() {

    val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    override val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun setQuestions(questions: List<Question>) {
        _questions.value = questions
    }

    fun getQuestions(): List<Question> {
        return _questions.value
    }
}
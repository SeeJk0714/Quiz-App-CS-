package com.seejiekai.quizappcs.data.repo

import com.seejiekai.quizappcs.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepo {
    fun getAllQuizzes(): Flow<List<Quiz>>

    suspend fun addQuiz(quiz: Quiz): String?

    suspend fun deleteQuiz(id: String)

    suspend fun updateQuiz(quiz: Quiz)

    suspend fun getQuizById(id: String): Quiz?
}
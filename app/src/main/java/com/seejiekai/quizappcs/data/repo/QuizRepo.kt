package com.seejiekai.quizappcs.data.repo

import com.seejiekai.quizappcs.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepo {
    fun getAllQuizzes(): Flow<List<Quiz>>

    fun getNewQuizById(): String

    suspend fun addQuiz(quiz: Quiz): String?

    suspend fun deleteQuiz(id: String)

    suspend fun updateQuiz(quiz: Quiz)

    suspend fun getQuizById(id: String): Quiz?

    suspend fun verifyAccessCode(accessCode: String): Quiz?
}
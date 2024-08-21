package com.seejiekai.quizappcs.data.repo

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.model.Quiz
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepoFirebase(
    private val authService: AuthService
): QuizRepo {
    private fun getCollection(): CollectionReference {
        val uid = authService.getUid() ?: throw Exception("User doesn't exist")
        return Firebase.firestore
            .collection("root_db/$uid/quizzes")
    }

    override fun getAllQuizzes() = callbackFlow<List<Quiz>> {
        val listener = getCollection().addSnapshotListener { value, error ->
            if (error != null) {
                throw error
            }

            val quizzes = mutableListOf<Quiz>()
            value?.documents?.map { item ->
                item.data?.let { quizMap ->
                    val quiz = Quiz.fromMap(quizMap)
                    quizzes.add(quiz.copy(id = item.id))
                }
            }
            trySend(quizzes)
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun addQuiz(quiz: Quiz): String? {
        val res = getCollection().add(quiz.toMap()).await()
        return res?.id
    }

    override suspend fun deleteQuiz(id: String) {
        getCollection().document(id).delete().await()
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        getCollection().document(quiz.id!!).set(quiz.toMap()).await()
    }

    override suspend fun getQuizById(id: String): Quiz? {
        val res = getCollection().document(id).get().await()
        Log.d("debugging","map: "+res.data.toString())
        return res.data?.let { Quiz.fromMap(it).copy(id = res.id) }
    }
}
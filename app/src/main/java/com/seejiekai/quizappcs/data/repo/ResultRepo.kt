package com.seejiekai.quizappcs.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seejiekai.quizappcs.data.model.Result
import kotlinx.coroutines.tasks.await

class ResultRepo {
    private fun getCollection(): CollectionReference {
        return Firebase.firestore
            .collection("results")
    }

    suspend fun addResult(result: Result): String? {
        val res = getCollection().add(result.toMap()).await()
        return res?.id
    }

    suspend fun checkTheResult(finishId: String, studentId: String): Boolean {
        val querySnapshot: QuerySnapshot = getCollection()
            .whereEqualTo("finishId", finishId)
            .whereEqualTo("studentId", studentId)
            .get()
            .await()

        return !querySnapshot.isEmpty
    }
}
package com.seejiekai.quizappcs.core.service

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthService(
    private val  context: Context
) {
    private val auth = FirebaseAuth.getInstance()

    suspend fun createUserWithEmailAndPass(email: String, pass: String): Boolean {
        val res = auth.createUserWithEmailAndPassword(
            email, pass
        ).await()

        return res.user != null
    }

    suspend fun loginWithEmailAndPass(email: String, pass: String): FirebaseUser? {
        val res = auth.signInWithEmailAndPassword(
            email, pass
        ).await()
        return res.user
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUid(): String? {
        return auth.currentUser?.uid
    }
}
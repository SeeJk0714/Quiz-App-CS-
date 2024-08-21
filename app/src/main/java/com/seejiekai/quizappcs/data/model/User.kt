package com.seejiekai.quizappcs.data.model

import com.seejiekai.quizappcs.core.utils.UserRoles

data class User(
    val userName: String,
    val email: String,
    val role: UserRoles = UserRoles.STUDENT,
    val profilePic: String? = null
){
    companion object {
        fun fromMap(map: Map<*,*>): User {
            return User(
                userName = map["userName"].toString(),
                email = map["email"].toString(),
                role = map["role"]?.let { UserRoles.valueOf(it.toString()) } ?: UserRoles.STUDENT,
                profilePic = map["profilePic"].toString()
            )
        }
    }
}

package com.seejiekai.quizappcs.data.model

data class User(
    val userName: String,
    val email: String,
    val role: String? = "Student",
    val profilePic: String? = null
){
    companion object {
        fun fromMap(map: Map<*,*>): User {
            return User(
                userName = map["userName"].toString(),
                email = map["email"].toString(),
                role = map["role"].toString(),
                profilePic = map["profilePic"].toString()
            )
        }
    }
}

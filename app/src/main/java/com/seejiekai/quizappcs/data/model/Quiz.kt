package com.seejiekai.quizappcs.data.model

data class Quiz(
    val id: String? = null,
    val quizId: String? = null,
    val quizName: String = "",
    val questions: List<Question> = emptyList()

){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "quizId" to quizId,
            "quizName" to quizName,
            "questions" to questions
        )
    }

    companion object {
        fun fromMap(map: Map<String,Any>): Quiz {
            return Quiz(
                quizId = map["quizId"].toString(),
                quizName = map["quizName"].toString(),
                questions = (map["questions"] as? List<Map<String, Any>>)?.map { Question.fromMap(it) } ?: emptyList()
            )
        }
    }
}
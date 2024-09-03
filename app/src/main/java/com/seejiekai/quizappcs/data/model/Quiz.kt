package com.seejiekai.quizappcs.data.model

data class Quiz(
    val quizId: String? = null,
    val accessCode: String? = null,
    val quizName: String = "",
    val description: String = "",
    val questions: List<Question> = emptyList(),
    val createBy: String? = null

){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "quizId" to quizId,
            "accessCode" to accessCode,
            "quizName" to quizName,
            "description" to description,
            "questions" to questions,
            "createBy" to createBy
        )
    }

    companion object {
        fun fromMap(map: Map<*,*>): Quiz {
            return Quiz(
                quizId = map["quizId"].toString(),
                accessCode = map["accessCode"].toString(),
                quizName = map["quizName"].toString(),
                description = map["description"].toString(),
                questions = (map["questions"] as? List<*>)?.map { question ->
                    Question.fromMap(question as Map<*, *>)
                } ?: emptyList(),
                createBy = map["createBy"].toString()
            )
        }
    }
}
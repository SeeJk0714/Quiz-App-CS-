package com.seejiekai.quizappcs.data.model

data class Question(
    val questionId: String = "",
    val question: String = "",
    val option: List<String> = emptyList(),
    val answer: String = "",
    val time: Int = 30,
    val mark: Int = 1
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "questionId" to questionId,
            "question" to question,
            "option" to option,
            "answer" to answer,
            "time" to time,
            "mark" to mark
        )
    }

    companion object {
        fun fromMap(map: Map<*,*>): Question {
            return Question(
                questionId = map["questionId"].toString(),
                question = map["question"].toString(),
                option = (map["option"] as? List<*>)?.map { it.toString() } ?: emptyList(),
                answer = map["answer"].toString(),
                time = map["time"].toString().toIntOrNull() ?: 30,
                mark = map["mark"].toString().toIntOrNull() ?: 1
            )
        }
    }
}

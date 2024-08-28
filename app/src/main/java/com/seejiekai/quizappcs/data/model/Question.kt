package com.seejiekai.quizappcs.data.model

data class Question(
    val id: String? = null,
    val question: String = "",
    val option: List<String> = emptyList(),
    val answer: String = "",
    val time: Int = 30,
    val mark: Int = 1
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "option" to option,
            "answer" to answer,
            "time" to time,
            "mark" to mark
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any>): Question {
            return Question(
                question = map["question"].toString(),
                option = (map["options"] as? List<String>) ?: emptyList(),
                answer = map["answer"].toString(),
                time = map["time"].toString().toIntOrNull() ?: 30,
                mark = map["mark"].toString().toIntOrNull() ?: 1
            )
        }
    }
}

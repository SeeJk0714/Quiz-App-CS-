package com.seejiekai.quizappcs.data.model

data class Quiz(
    val id: String? = null,
    val title: String = "",
    val desc: String = "",
    val priority: Int = 0,
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "desc" to desc,
            "priority" to priority,
        )
    }

    companion object {
        fun fromMap(map: Map<String,Any>): Quiz {
            return Quiz(
                title = map["title"].toString(),
                desc = map["desc"].toString(),
                priority = map["priority"].toString().toIntOrNull() ?: 0
            )
        }
    }
}
package com.seejiekai.quizappcs.data.model

data class Result(
    var studentName: String = "",
    val studentId: String ?= null,
    val finishId: String ?= null,
    val mark: Int = 0,

    ){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "studentName" to studentName,
            "studentId" to studentId,
            "finishId" to finishId,
            "mark" to mark
        )
    }

    companion object {
        fun fromMap(map: Map<*,*>): Result {
            return Result(
                studentName = map["studentName"].toString(),
                studentId = map["studentId"].toString(),
                finishId = map["finishId"].toString(),
                mark = map["mark"].toString().toIntOrNull() ?: 0
            )
        }
    }
}

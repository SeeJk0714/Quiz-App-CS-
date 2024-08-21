package com.seejiekai.quizappcs.core.utils

import com.seejiekai.quizappcs.data.model.ValidationField

object ValidationUtil {
    fun validate(vararg fields: ValidationField): String? {
        fields.forEach {field ->
            if (!Regex(field.regExp).matches(field.value)) {
                return field.errMsg
            }
        }
        return null
    }
}
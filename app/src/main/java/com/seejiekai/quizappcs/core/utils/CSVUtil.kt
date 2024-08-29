package com.seejiekai.quizappcs.core.utils

import android.content.Context
import android.net.Uri
import com.seejiekai.quizappcs.data.model.Question
import java.io.BufferedReader
import java.io.InputStreamReader

// This code essentially allows the application to read
// questions from a CSV file and convert them into a list of Question objects that can be used within the app.
object CSVUtil {
    fun readCSV (context: Context, uri: Uri): List<Question> {
        val questions = mutableListOf<Question>()
        try {
            // this line is used to get a stream of data from a file or resource located at the URI, allowing you to read its contents.
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                // this line sets up a BufferedReader to read text data from the InputStream efficiently.
                val reader = BufferedReader(InputStreamReader(inputStream))

                reader.readLine()
                reader.forEachLine { line ->
                    val content = line.split(",")
                    if (content.size >= 6) {
                        val question = Question(
                            question = content[0],
                            option = content.subList(1,5),
                            answer = content[5],
                            time = content[6].toIntOrNull() ?: 10,
                            mark = content[7].toIntOrNull() ?: 1
                        )
                        questions.add(question)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return questions
    }

}
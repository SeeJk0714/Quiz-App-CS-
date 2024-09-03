package com.seejiekai.quizappcs.ui.student.ranking

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.seejiekai.quizappcs.data.model.Result
import com.seejiekai.quizappcs.data.model.User
import kotlinx.coroutines.tasks.await

@HiltViewModel
class RankViewModel @Inject constructor() : BaseViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _studentMarks = MutableStateFlow<List<Result>>(emptyList())
    val studentMarks: StateFlow<List<Result>> = _studentMarks

    init {
        fetchStudentMarks()
    }

    private fun fetchStudentMarks() {
        viewModelScope.launch {
            try {
                Log.d("FetchStudentMarks", "Starting fetch")

                // Simplified query to fetch all documents
                val snapshot = db.collection("results").get().await()

                Log.d("FetchStudentMarks", "Snapshot size: ${snapshot.size()}")

                if (snapshot.isEmpty) {
                    Log.d("FetchStudentMarks", "No documents found")
                }

                val marksList = mutableListOf<Result>()
                for (document in snapshot.documents) {

                    val data = document.data
                    Log.d("FetchStudentMarks", "Document data: $data")

                    val studentMark = document.toObject(Result::class.java)
                    studentMark?.let { mark ->
                        Log.d("FetchStudentMarks", "Fetched mark: $mark")
                        marksList.add(mark)
                    }
                }

                Log.d("FetchStudentMarks", "Marks list: $marksList")
                _studentMarks.value = marksList
            } catch (e: Exception) {
                Log.e("FetchStudentMarks", "Error fetching student marks: ${e.message}", e)
            }
        }
    }

}
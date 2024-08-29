package com.seejiekai.quizappcs.ui.addEditCSV.edit

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.core.utils.CSVUtil
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCSVFragment: BaseManageFragment() {
    override val viewModel: EditCSVViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_base_manage
    private lateinit var adapter: QuestionAdapter
    private lateinit var csvPickerLauncher: ActivityResultLauncher<String>


    override fun onBindView(view: View) {
        super.onBindView(view)
        setupQuestionAdapter()
        refreshCSV()

        binding?.btnSubmit?.text = getString(R.string.update)

        binding?.btnSubmit?.setOnClickListener {
            val quizName = binding?.etQuizName?.text.toString()

            viewModel.uploadCSV(quizName)
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                quiz?.let {
                    Log.d("quizname", it.quizName)
                    binding?.etQuizName?.setText(it.quizName)
                    adapter.setQuestions(it.questions)
                }
            }
        }
    }

    private fun setupQuestionAdapter() {
        adapter = QuestionAdapter(emptyList())
        binding?.rvQuestion?.adapter = adapter
        binding?.rvQuestion?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun refreshCSV() {
        // This line initializes "csvPickerLauncher" to handle the result of picking a file.
        csvPickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){ uri: Uri? ->
            uri?.let {
                val questions = CSVUtil.readCSV(requireContext(), it)
                if (questions.isNotEmpty()) {
                    viewModel.setQuestions(questions)
                    adapter.setQuestions(questions)
                    binding?.tvCSV?.isInvisible = adapter.itemCount != 0
                }
            }
        }

        // This line sets up a click listener for a button "btnUploadCSV" that triggers the CSV file picker when clicked.
        binding?.btnUploadCSV?.setOnClickListener {
            csvPickerLauncher.launch("text/*")
        }
    }

}
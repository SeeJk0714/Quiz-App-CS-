package com.seejiekai.quizappcs.ui.addEditCSV.base

import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.core.utils.CSVUtil
import com.seejiekai.quizappcs.databinding.FragmentBaseManageBinding
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.base.BaseFragment
import kotlinx.coroutines.launch

abstract class BaseManageFragment : BaseFragment<FragmentBaseManageBinding>() {
    override val viewModel: BaseManageViewModel by viewModels()
    private lateinit var adapter: QuestionAdapter
    private lateinit var csvPickerLauncher: ActivityResultLauncher<String>

    override fun onBindView(view: View) {
        binding = DataBindingUtil.bind(view)
        setupQuestionAdapter()
        refreshCSV()
    }

    override fun onBindData(view: View) {
        lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack()
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
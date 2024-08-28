package com.seejiekai.quizappcs.ui.addEditCSV.edit

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.core.utils.CSVUtil
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageFragment
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCSVFragment: BaseManageFragment() {
    override val viewModel: EditCSVViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_base_manage
    private lateinit var adapter: QuestionAdapter
    private val args: EditCSVFragmentArgs by navArgs()

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupQuestionAdapter()


        binding?.btnSubmit?.setOnClickListener {
            val quizName = binding?.etQuizName?.text.toString()

            viewModel.uploadCSV(quizName)
        }


        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                quiz?.let {
                    Log.d("debugging", adapter.setQuestions(it.questions).toString())
                    binding?.etQuizName?.setText(it.quizName)
                    adapter.setQuestions(it.questions)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        viewModel.getQuizById(args.quizId)
    }

    private fun setupQuestionAdapter() {
        adapter = QuestionAdapter(emptyList())
        binding?.rvQuestion?.adapter = adapter
        binding?.rvQuestion?.layoutManager = LinearLayoutManager(requireContext())
    }

}
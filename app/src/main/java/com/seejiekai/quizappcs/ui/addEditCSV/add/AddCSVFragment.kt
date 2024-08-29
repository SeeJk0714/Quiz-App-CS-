package com.seejiekai.quizappcs.ui.addEditCSV.add

import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.core.utils.CSVUtil
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCSVFragment: BaseManageFragment() {
    override val viewModel: AddCSVViewModel by viewModels()
    private lateinit var adapter: QuestionAdapter

    override fun getLayoutResource() = R.layout.fragment_base_manage
    override fun onBindView(view: View) {
        super.onBindView(view)
//        setupQuestionAdapter()

        binding?.btnSubmit?.setOnClickListener {
            val name = binding?.etQuizName?.text.toString()

            viewModel.uploadCSV(name)
        }
    }

//    private fun setupQuestionAdapter() {
//        adapter = QuestionAdapter(emptyList())
//        binding?.rvQuestion?.adapter = adapter
//        binding?.rvQuestion?.layoutManager = LinearLayoutManager(requireContext())
//    }
}
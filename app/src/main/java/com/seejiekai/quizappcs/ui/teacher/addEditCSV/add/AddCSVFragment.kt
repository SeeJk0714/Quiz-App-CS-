package com.seejiekai.quizappcs.ui.teacher.addEditCSV.add

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.core.utils.CSVUtil
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.teacher.addEditCSV.base.BaseManageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCSVFragment: BaseManageFragment() {
    override val viewModel: AddCSVViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_base_manage
    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.run {
            btnSubmit.setOnClickListener {
                val name = etQuizName.text.toString()
                val desc = etQuizDesc.text.toString()
                viewModel.uploadCSV(name, desc)
            }
        }
    }
}
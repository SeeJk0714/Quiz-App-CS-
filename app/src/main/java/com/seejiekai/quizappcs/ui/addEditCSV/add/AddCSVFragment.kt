package com.seejiekai.quizappcs.ui.addEditCSV.add

import android.view.View
import androidx.fragment.app.viewModels
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.ui.addEditCSV.base.BaseManageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCSVFragment: BaseManageFragment() {
    override val viewModel: AddCSVViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_base_manage
    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.btnSubmit?.setOnClickListener {
            val name = binding?.etQuizName?.text.toString()

            viewModel.uploadCSV(name)
        }
    }

}
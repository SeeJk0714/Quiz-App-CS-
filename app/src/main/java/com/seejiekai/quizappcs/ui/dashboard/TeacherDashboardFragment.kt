package com.seejiekai.quizappcs.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentTeacherDashboardBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import com.seejiekai.quizappcs.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherDashboardFragment : BaseFragment<FragmentTeacherDashboardBinding>() {
    override val viewModel: TeacherDashboardViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_teacher_dashboard

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.btnLogOut?.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                TeacherDashboardFragmentDirections.actionTeacherDashboardToLogin()
            )
        }
    }
}
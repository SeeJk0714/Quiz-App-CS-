package com.seejiekai.quizappcs.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentHomeBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_home

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.btnLogOut?.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToLogin()
            )
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
    }

}
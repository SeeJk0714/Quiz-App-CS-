package com.seejiekai.quizappcs.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentLoginBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by  viewModels()

    override fun getLayoutResource() = R.layout.fragment_login

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.etEmail?.text.toString()
            val password = binding?.etPassword?.text.toString()
            viewModel.login(email, password)
        }

        binding?.btnRegisterPage?.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginToRegister()
            )
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.success.collect {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginToHome()
                )
            }
        }
    }
}
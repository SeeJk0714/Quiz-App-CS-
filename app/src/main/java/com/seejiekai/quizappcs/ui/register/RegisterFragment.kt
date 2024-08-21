package com.seejiekai.quizappcs.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentRegisterBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: RegisterViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_register

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.run {
            btnRegister.setOnClickListener {
                viewModel.createUser(
                    userName = etUsername.text.toString(),
                    email = etEmail.text.toString(),
                    pass = etPassword.text.toString(),
                    confirmPass = etConfirmPassword.text.toString(),
                    role = ""
                )
            }
        }

        binding?.btnLoginPage?.setOnClickListener {
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterToLogin()
            )
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.success.collect {
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterToHome()
                )
            }
        }
    }

}
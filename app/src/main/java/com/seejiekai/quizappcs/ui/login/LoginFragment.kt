package com.seejiekai.quizappcs.ui.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.core.utils.UserRoles
import com.seejiekai.quizappcs.databinding.FragmentLoginBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by  viewModels()

    override fun getLayoutResource() = R.layout.fragment_login

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupButton()
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.success.collect { role ->
                findNavController().navigate(
                    when(role) {
                        UserRoles.STUDENT -> LoginFragmentDirections.actionLoginToHome()
                        UserRoles.TEACHER -> LoginFragmentDirections.actionLoginToTeacherDashboard()
                    }
                )
            }
        }
    }

    private fun setupButton() {
        binding?.run {
            btnLogin.setOnClickListener {
                val email = binding?.etEmail?.text.toString()
                val password = binding?.etPassword?.text.toString()
                viewModel.login(email, password)
            }

            btnRegisterPage.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginToRegister()
                )
            }
        }
    }
}
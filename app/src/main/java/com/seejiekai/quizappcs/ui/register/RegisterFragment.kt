package com.seejiekai.quizappcs.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
            //It sets up a list of options (like "STUDENT" and "TEACHER") that you want to show in a dropdown list.
            val roleAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.role_array,
                android.R.layout.simple_spinner_item
            )
            //Chooses how the list of options looks when the dropdown menu is open.
            roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Connects the list of options (adapter) to the Spinner so it displays them.
            sRole.adapter = roleAdapter

            btnRegister.setOnClickListener {
                viewModel.createUser(
                    userName = etUsername.text.toString(),
                    email = etEmail.text.toString(),
                    pass = etPassword.text.toString(),
                    confirmPass = etConfirmPassword.text.toString(),
                    //If a user selects "Student" from the Spinner, selectedItem would be "Student".
                    role = sRole.selectedItem.toString()
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
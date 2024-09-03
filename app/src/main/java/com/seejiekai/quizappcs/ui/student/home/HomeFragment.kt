package com.seejiekai.quizappcs.ui.student.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.databinding.AlertViewQuizidBinding
import com.seejiekai.quizappcs.databinding.FragmentHomeBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_home
    private var accessCode: String = ""

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupButton()

        lifecycleScope.launch {
            viewModel.quizExists.collect {
                when(it) {
                    true -> {
                        Log.d("accessCode", "accessCode: Found")
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeToStudentQuiz(
                                accessCode
                            )
                        )
                    }
                    false -> {
                        Log.d("accessCode", "accessCode: Not Found")
                        Toast.makeText(
                            requireContext(),
                            "AccessCode doesn't exist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    null -> {
                        Toast.makeText(
                            requireContext(),
                            "....",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupButton() {
        binding?.run {
            fabAccessCode.setOnClickListener {
                if (cvInputBox.visibility == View.VISIBLE) {
                    cvInputBox.visibility = View.GONE
                } else {
                    cvInputBox.visibility = View.VISIBLE
                }
            }

            btnsubmit.setOnClickListener {
                accessCode = binding?.etAccessCode?.text.toString()
                Log.d("accessCode entered", accessCode)
                lifecycleScope.launch {
                    if (accessCode.isNotEmpty()) {
                        val checkResult = viewModel.checkTheResult(accessCode)
                        if (!checkResult) {
                            viewModel.verifyAccessCode(accessCode)
                        } else {
                            Snackbar.make(requireView(), "You have finished this quiz", Snackbar.LENGTH_SHORT).show()
                        }
                    } else {
                        Snackbar.make(requireView(), "Please enter a AccessCode", Snackbar.LENGTH_SHORT).show()
                    }
                }

            }

            mcRank.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToRank()
                )
            }

            btnLogOut.setOnClickListener {
                viewModel.logout()
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToLogin()
                )
            }
        }
    }

}
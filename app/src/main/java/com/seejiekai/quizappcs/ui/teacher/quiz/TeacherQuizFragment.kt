package com.seejiekai.quizappcs.ui.teacher.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.databinding.AlertViewQuizidBinding
import com.seejiekai.quizappcs.databinding.AlretViewBinding
import com.seejiekai.quizappcs.databinding.FragmentTeacherQuizBinding
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.adapter.QuizAdapter
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import com.seejiekai.quizappcs.ui.student.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherQuizFragment : BaseFragment<FragmentTeacherQuizBinding>() {
    override val viewModel: TeacherQuizViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_teacher_quiz
    private lateinit var adapter: QuizAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupQuizAdapter()
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.getAllQuiz().collect { quizzes ->
                adapter.setQuizzes(quizzes)
                binding?.tvNotQuiz?.isInvisible = adapter.itemCount != 0
            }
        }
    }

    private fun setupQuizAdapter() {
        adapter = QuizAdapter(emptyList())
        binding?.rvQuiz?.adapter = adapter
        binding?.rvQuiz?.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = object: QuizAdapter.Listener {
            override fun onClick(quiz: Quiz) {
                findNavController().navigate(
                    TeacherQuizFragmentDirections.actionTeacherQuizToViewQuiz(quiz.quizId!!)
                )
            }

            override fun onEdit(quiz: Quiz) {
                findNavController().navigate(
                    TeacherQuizFragmentDirections.actionTeacherQuizToEditCSV(quiz.quizId!!)
                )
            }

            override fun onDelete(quiz: Quiz) {
                alertShowDelete(quiz.quizId!!)
            }
        }
    }

    private fun alertShowDelete(id: String) {
        val alertView = AlretViewBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(alertView.root)
        val dialog = alertDialog.create()

        alertView.run {
            btnShowDelete.setOnClickListener {
                viewModel.deleteQuiz(id)
                dialog.dismiss()
            }


            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }
    }
}
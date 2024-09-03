package com.seejiekai.quizappcs.ui.teacher.viewQuiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentViewQuizBinding
import com.seejiekai.quizappcs.ui.adapter.QuestionAdapter
import com.seejiekai.quizappcs.ui.adapter.QuizAdapter
import com.seejiekai.quizappcs.ui.base.BaseFragment
import com.seejiekai.quizappcs.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewQuizFragment : BaseFragment<FragmentViewQuizBinding>() {
    override val viewModel: ViewQuizViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_view_quiz
    private lateinit var adapter: QuestionAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupQuestionAdapter()

    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                if (quiz != null) {
                    binding?.tvQuizName?.text = quiz.quizName
                    binding?.tvDesc?.text = quiz.description
                    quiz.questions.let { questions ->
                        adapter.setQuestions(questions)
                    }
                }
            }
        }
    }



    private fun setupQuestionAdapter() {
        adapter = QuestionAdapter(emptyList())
        binding?.rvQuestion?.adapter = adapter
        binding?.rvQuestion?.layoutManager = LinearLayoutManager(requireContext())
    }
}
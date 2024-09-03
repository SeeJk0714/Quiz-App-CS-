package com.seejiekai.quizappcs.ui.student.quiz

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentStudentQuizBinding
import com.seejiekai.quizappcs.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentQuizFragment : BaseFragment<FragmentStudentQuizBinding>() {
    override val viewModel: StudentQuizViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_student_quiz
    private var mark = 0
    private var countDownTimer: CountDownTimer ?= null
    private val args: StudentQuizFragmentArgs by navArgs()

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupButton()

    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                binding?.tvQuizName?.text = quiz?.quizName
                setupQuestion()

            }
        }

        lifecycleScope.launch {
            viewModel.currentQuestionIndex.collect {
                setupQuestion()
            }
        }
    }

    private fun setupButton() {
        binding?.run {
            btnSubmit.setOnClickListener {
                submitAns()
            }

            btnNext.setOnClickListener {
                nextQuestion()
            }
        }
    }

    private fun submitAns() {
        val selectOptionId = binding?.rgOptions?.checkedRadioButtonId
        if (selectOptionId == -1) {
            Toast.makeText(context,"Please select an option", Toast.LENGTH_SHORT).show()
        } else {
            val selectedRadioButton = selectOptionId?.let { binding?.rgOptions?.findViewById<RadioButton>(it) }
            val selectedAns = selectedRadioButton?.text.toString()
            val currentIndex = viewModel.currentQuestionIndex.value
            val correctAns = currentIndex.let { viewModel.quiz.value?.questions?.get(it)?.answer }

            checkingAnswerCorrect(selectedAns, correctAns)
        }
        Log.d("totalMark", mark.toString())
    }

    private fun checkingAnswerCorrect(selectedAns: String, correctAns: String?) {
        binding?.run {
            if (selectedAns == correctAns) {
                tvCorrectAnswer.text = "Correct Answer"
                tvCorrectAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                mark++
            } else {
                tvCorrectAnswer.text = "Incorrect Answer!!! The correct answer is $correctAns"
                tvCorrectAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
            tvCorrectAnswer.visibility = View.VISIBLE
            btnSubmit.visibility = View.INVISIBLE
            btnNext.visibility = View.VISIBLE
            countDownTimer?.cancel()
        }
    }

    private fun nextQuestion() {
        val currentIndex = viewModel.currentQuestionIndex.value
        val totalQuestions = viewModel.quiz.value?.questions?.size ?: 0
        Log.d("btnnext", currentIndex.toString())
        checkingQuestionPage(currentIndex, totalQuestions)
    }

    private fun checkingQuestionPage(currentIndex: Int, totalQuestions: Int) {
        binding?.run {
            if (currentIndex + 1 < totalQuestions) {
                viewModel.goToNextQuestion()
                tvCorrectAnswer.visibility = View.INVISIBLE
                btnSubmit.visibility = View.VISIBLE
                btnNext.visibility = View.INVISIBLE
                cvTimeUp.visibility = View.INVISIBLE
            } else {
                btnNext.text = "Done"
                submitResult()
                findNavController().navigate(
                    StudentQuizFragmentDirections.actionStudentQuizToHome()
                )
            }
        }
    }

    private fun submitResult() {
        lifecycleScope.launch {
            val quizId = args.accessId
            val totalMark = mark
            viewModel.addResult(quizId, totalMark)
        }
    }

    private fun setupQuestion() {
        val quiz = viewModel.quiz.value
        val index = viewModel.currentQuestionIndex.value
        val question = index.let { quiz?.questions?.getOrNull(it) }

        if (question != null) {
            binding?.run {
                tvQuestionText.text = question.question
                tvMark.text = question.mark.toString()
                val options = listOf(rbOption1, rbOption2, rbOption3, rbOption4)
                options.forEachIndexed { index, option ->
                    // Set the text for each option
                    Log.d("option", question.option[index])
                    option.text = question.option[index]
                }
                rgOptions.clearCheck()
                countingDownTimer(question.time)
            }
        }
    }

    private fun countingDownTimer(time: Int) {
        countDownTimer?.cancel()
        binding?.run {
            if (time > 0) {
                tvTime.text = "$time"
                countDownTimer = object : CountDownTimer(time * 1000L, 1000L) {
                    override fun onTick(millisUntilFinished: Long) {
                        val timeLeft = millisUntilFinished / 1000
                        tvTime.text = "$timeLeft"
                    }

                    override fun onFinish() {
                        tvTime.text = "00"
                        cvTimeUp.visibility = View.VISIBLE
                        btnNext.visibility = View.VISIBLE
                        btnSubmit.visibility = View.INVISIBLE
                    }
                }.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
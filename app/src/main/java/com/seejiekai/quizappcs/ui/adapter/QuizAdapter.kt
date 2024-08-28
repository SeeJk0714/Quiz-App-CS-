package com.seejiekai.quizappcs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.animation.AnimatableView.Listener
import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.model.Quiz
import com.seejiekai.quizappcs.databinding.QuestionItemLayoutBinding
import com.seejiekai.quizappcs.databinding.QuizItemLayoutBinding

class QuizAdapter(
    private var quizzes: List<Quiz>,
//    private val authService: AuthService

): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    var listener: Listener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = QuizItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun getItemCount() = quizzes.size

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val item = quizzes[position]
        holder.bind(item)
    }

    fun setQuizzes(quizzes: List<Quiz>) {
        this.quizzes = quizzes
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: QuizItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding?.run {
                tvQuizID.text = quiz.quizId
                tvQuizName.text = quiz.quizName
//                tvTeacher.text =

                cvQuiz.setOnClickListener {
                    listener?.onClick(quiz)
                }

                ivEdit.setOnClickListener {
                    listener?.onEdit(quiz)
                }

                ivDelete.setOnClickListener {
                    listener?.onDelete(quiz)
                }
            }
        }
    }

    interface Listener {
        fun onClick(quiz: Quiz)
        fun onEdit(quiz: Quiz)
        fun onDelete(quiz: Quiz)
    }
}
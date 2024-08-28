package com.seejiekai.quizappcs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seejiekai.quizappcs.data.model.Question
import com.seejiekai.quizappcs.databinding.QuestionItemLayoutBinding

class QuestionAdapter(
    private var questions: List<Question>
): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder{
        val binding = QuestionItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun getItemCount() = questions.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)

    }

    fun setQuestions(questions: List<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    inner class QuestionViewHolder(
        private val binding: QuestionItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.run {
                tvID.text = question.id
                tvQuestion.text = question.question
                tvOption1.text = question.option[0]
                tvOption2.text = question.option[1]
                tvOption3.text = question.option[2]
                tvOption4.text = question.option[3]
                tvAns.text = question.answer
            }
        }
    }
}
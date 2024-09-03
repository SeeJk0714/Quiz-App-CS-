package com.seejiekai.quizappcs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seejiekai.quizappcs.data.model.Result
import com.seejiekai.quizappcs.databinding.RankItemLayoutBinding

class ResultAdapter: ListAdapter<Result, ResultAdapter.ResultViewHolder>(ResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = RankItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ResultViewHolder(
        private val binding: RankItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.run {
                tvStudentName.text = result.studentId
                tvMark.text = result.mark.toString()
            }
        }
    }

    class ResultDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.studentId == newItem.studentId
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}
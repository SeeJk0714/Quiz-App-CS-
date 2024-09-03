package com.seejiekai.quizappcs.ui.student.ranking

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.seejiekai.quizappcs.R
import com.seejiekai.quizappcs.databinding.FragmentRankBinding
import com.seejiekai.quizappcs.ui.adapter.ResultAdapter
import com.seejiekai.quizappcs.ui.base.BaseFragment
import kotlinx.coroutines.launch

class RankFragment : BaseFragment<FragmentRankBinding>() {
    override val viewModel: RankViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_rank

    private lateinit var adapter: ResultAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupAdapter()
        observeViewModel()
    }

    private fun setupAdapter() {
        adapter = ResultAdapter()
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.studentMarks.collect { marks ->
                // Assuming `adapter` is a property of your fragment and properly initialized
                adapter.submitList(marks)
            }
        }
    }
}
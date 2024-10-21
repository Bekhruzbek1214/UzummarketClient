package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenSearchForCategoryByBinding
import com.example.uzummarketclient.presenter.adapters.SearchForCategoryByAdapter
import com.example.uzummarketclient.presenter.viewModel.SearchForCategoryByVM
import com.example.uzummarketclient.presenter.viewModel.impl.SearchForCategoryByVMImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchForCategoryByScreen : Fragment(R.layout.screen_search_for_category_by) {
    private val viewModel: SearchForCategoryByVM by viewModels<SearchForCategoryByVMImpl>()
    private val binding by viewBinding(ScreenSearchForCategoryByBinding::bind)
    private val adapter = SearchForCategoryByAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initFlow() = binding.apply {

    }

    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = GridLayoutManager(requireContext(), 2)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        etSearch.addTextChangedListener {
            viewModel
        }
        adapter.setOnClickProduct {
            parentFragmentManager.popBackStack()
        }
    }
}
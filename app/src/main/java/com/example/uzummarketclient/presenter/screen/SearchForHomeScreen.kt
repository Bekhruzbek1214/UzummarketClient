package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenSearchForHomeBinding
import com.example.uzummarketclient.presenter.adapters.SearchForMainAdapter
import com.example.uzummarketclient.presenter.viewModel.SearchForHomeVM
import com.example.uzummarketclient.presenter.viewModel.impl.SearchForHomeVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchForHomeScreen : Fragment(R.layout.screen_search_for_home) {
    private val viewModel: SearchForHomeVM by viewModels<SearchForHomeVMImpl>()
    private val binding by viewBinding(ScreenSearchForHomeBinding::bind)
    private val adapter = SearchForMainAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = GridLayoutManager(requireContext(), 2)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        etSearch.addTextChangedListener {
            viewModel.textChange(etSearch.text.toString())
        }
        adapter.setOnClickProduct {
            viewModel.onClickProduct(it)
            parentFragmentManager.popBackStack()
        }
    }

    private fun initFlow() = binding.apply {
        viewModel.resultProducts
            .onEach {
                adapter.submitList(it)
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}
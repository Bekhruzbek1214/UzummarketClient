package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenHomeBinding
import com.example.uzummarketclient.presenter.adapters.HomeScreenAdapter
import com.example.uzummarketclient.presenter.viewModel.HomeVM
import com.example.uzummarketclient.presenter.viewModel.impl.HomeVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeVM by viewModels<HomeVMImpl>()
    private val adapter = HomeScreenAdapter()
    private var time = System.currentTimeMillis()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadProducts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initFlow() = binding.apply {
        viewModel.allCategoryByProductData
            .onEach { adapter.submitList(it)}
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        viewModel.errorMessage
            .onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
        viewModel.progressState
            .onEach {
                progress.isGone = it
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun initView() = binding.apply {
        rvListOuter.adapter=adapter
        rvListOuter.layoutManager = LinearLayoutManager(requireContext())
        etSearch.setOnClickListener {
            if (System.currentTimeMillis() - time >= 500) {
                time = System.currentTimeMillis()
                viewModel.onClickSearch()
                parentFragmentManager
                    .beginTransaction()
                    .add(binding.container.id, SearchForHomeScreen())
                    .addToBackStack("")
                    .commit()
            }
        }

        adapter.setOnClickCategory {
            viewModel.onClickCategory(it)
            parentFragmentManager
                .beginTransaction()
                .add(container.id, CategoryByProductsScreen())
                .addToBackStack("")
                .commit()
        }
        adapter.setOnClickProduct {
            viewModel.onClickProduct(it)
        }
    }
}
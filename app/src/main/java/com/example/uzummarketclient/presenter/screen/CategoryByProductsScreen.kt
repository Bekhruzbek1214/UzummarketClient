package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.databinding.ScreenCategoryByProductsBinding
import com.example.uzummarketclient.presenter.adapters.CategoryByProductAdapter
import com.example.uzummarketclient.presenter.viewModel.CategoryByPrVM
import com.example.uzummarketclient.presenter.viewModel.impl.CategoryByPPageVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CategoryByProductsScreen : Fragment(R.layout.screen_category_by_products) {
    private val binding by viewBinding(ScreenCategoryByProductsBinding::bind)
    private val viewModel: CategoryByPrVM by viewModels<CategoryByPPageVMImpl>()
    private val adapter = CategoryByProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()


    }

    private fun initFlow() = binding.apply {
        viewModel.category
            .onEach {
                adapter.submitList(it?.productsList)
                categoryName.text = it?.categoryName
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.setOnClickProduct {
            viewModel.onClickProduct(it)
            parentFragmentManager.popBackStack()
        }
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        btnSearch.setOnClickListener {
            viewModel.onClickSearch()
            parentFragmentManager
                .beginTransaction()
                .add(binding.container.id, SearchForCategoryByScreen())
                .addToBackStack("")
                .commit()
        }
        viewModel.categoryRequest()
    }
}
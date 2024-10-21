package com.example.uzummarketclient.presenter.viewModel


import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HomeVM {
    val allCategoryByProductData : StateFlow<List<CategoryByProductData>>
    val errorMessage : Flow<String>
    val progressState : StateFlow<Boolean>
    fun onClickSearch()

    fun onClickCategory(category: CategoryByProductData)

    fun onClickProduct(product: ProductByMainData)

    fun loadProducts()
}
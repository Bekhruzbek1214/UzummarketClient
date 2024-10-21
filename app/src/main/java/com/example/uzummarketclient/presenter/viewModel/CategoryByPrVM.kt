package com.example.uzummarketclient.presenter.viewModel

import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import kotlinx.coroutines.flow.StateFlow

interface CategoryByPrVM {
    val category : StateFlow<CategoryByProductData?>
    fun onClickProduct(product: ProductByMainData)

    fun categoryRequest()
    fun onClickSearch()
}
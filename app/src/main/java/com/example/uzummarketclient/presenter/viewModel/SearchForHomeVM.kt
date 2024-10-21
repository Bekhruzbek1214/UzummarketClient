package com.example.uzummarketclient.presenter.viewModel

import com.example.uzummarketclient.data.model.ProductByMainData
import kotlinx.coroutines.flow.StateFlow

interface SearchForHomeVM {

    val resultProducts : StateFlow<List<ProductByMainData>>

    fun textChange(text : String)

    fun onClickProduct(productByMainData: ProductByMainData)
}
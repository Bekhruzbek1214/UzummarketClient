package com.example.uzummarketclient.presenter.viewModel

import com.example.uzummarketclient.data.model.ProductData
import kotlinx.coroutines.flow.Flow

interface BasketVM  {
    val successSetOrderFlow:Flow<Unit>
    val errorSetOrderFlow:Flow<String>
    fun loadProducts():Flow<Pair<ArrayList<ProductData>,List<Int>>>
    fun clickProduct(data: ProductData)
    fun deleteProduct(productId:String)
    fun setOrder(balance:Int)
    fun onClickOrder()
}
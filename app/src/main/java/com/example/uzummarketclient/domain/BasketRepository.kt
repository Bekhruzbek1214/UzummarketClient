package com.example.uzummarketclient.domain

import com.example.uzummarketclient.data.model.ProductData
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun getProducts(data:List<String>): Flow<ArrayList<ProductData>>
    fun setBasket(balance:Int):Flow<Result<Unit>>
}
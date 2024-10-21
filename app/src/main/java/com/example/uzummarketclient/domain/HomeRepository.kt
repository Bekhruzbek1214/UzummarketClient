package com.example.uzummarketclient.domain

import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getAllCategoryByProduct() : Flow<Result<List<Pair<String,String>>>>
    fun getProductInCategory(categoryid: String, categoryName: String): Flow<ArrayList<ProductByMainData>>

    fun setCategory(category: CategoryByProductData)

    fun getCategory() : CategoryByProductData

    fun getProductsByName(name : String) : List<ProductByMainData>

    fun setAllCategoryByProductsData(list : List<CategoryByProductData>)
}
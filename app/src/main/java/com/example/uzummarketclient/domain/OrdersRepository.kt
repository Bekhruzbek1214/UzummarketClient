package com.example.uzummarketclient.domain

import kotlinx.coroutines.flow.Flow


interface OrdersRepository {
    fun getAllMyOrder():Flow<List<Pair<Int,ArrayList<String>>>>
    fun getActiveMyOrder():Flow<List<Pair<Int,ArrayList<String>>>>
    fun getProductName(productsId:List<String>):Flow<ArrayList<String>>
}
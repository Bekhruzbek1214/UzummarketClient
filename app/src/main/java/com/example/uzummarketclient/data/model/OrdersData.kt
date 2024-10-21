package com.example.uzummarketclient.data.model

data class OrdersData(
    val countArr:ArrayList<Int>,
    val productArr:ArrayList<String>,
    val status:ArrayList<Boolean>,
    val totalBalance:Int,
    val userId: String
)
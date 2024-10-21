package com.example.uzummarketclient.data.model

data class ProductData(
    var id:String,
    var images:List<String>?,
    var category: String?,
    var description:String?,
    var productName:String?,
    var sellerName:String?,
    var cost:String?
)
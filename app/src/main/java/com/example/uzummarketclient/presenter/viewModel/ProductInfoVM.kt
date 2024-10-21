package com.example.uzummarketclient.presenter.viewModel

interface ProductInfoVM {

    fun setProductInBasket(productId:String)
    fun isHaveProductInBasked(productId: String):Boolean
    fun backScreen()
}
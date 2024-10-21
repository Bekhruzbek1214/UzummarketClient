package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.viewModel.ProductInfoVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductInfoVMImpl @Inject constructor(
    private val navigation: AppNavigator,
) : ViewModel(), ProductInfoVM {
    private val shared=MyShar
    override fun setProductInBasket(productId: String) {
        val basket=shared.getBasket()
        basket.productsId.add(productId)
        basket.countsArr.add(1)
        shared.setBasket(basket)
    }

    override fun isHaveProductInBasked(productId: String): Boolean {
        val basket=shared.getBasket()
        return basket.productsId.contains(productId)
    }

    override fun backScreen() {
        viewModelScope.launch {
            navigation.popBackStack()
        }
    }

}
package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.domain.HomeRepository
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.screen.MainScreenDirections
import com.example.uzummarketclient.presenter.viewModel.SearchForHomeVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchForHomeVMImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val homeRepository: HomeRepository
) : ViewModel(), SearchForHomeVM {
    override val resultProducts = MutableStateFlow<List<ProductByMainData>>(arrayListOf())
    override fun textChange(text: String) {
        resultProducts.value = homeRepository.getProductsByName(text)
    }

    override fun onClickProduct(productByMainData: ProductByMainData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToProductInfoScreen(productByMainData))
        }
    }

}
package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.domain.HomeRepository
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.screen.MainScreenDirections
import com.example.uzummarketclient.presenter.viewModel.CategoryByPrVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryByPPageVMImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: HomeRepository
) : ViewModel(), CategoryByPrVM {
    override val category = MutableStateFlow<CategoryByProductData?>(null)

    override fun onClickProduct(product: ProductByMainData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToProductInfoScreen(product))
        }
    }

    override fun categoryRequest() {
        category.value = repository.getCategory()
    }

    override fun onClickSearch() {

    }
}
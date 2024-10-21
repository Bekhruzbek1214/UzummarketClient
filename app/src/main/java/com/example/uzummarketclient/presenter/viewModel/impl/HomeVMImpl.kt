package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.domain.HomeRepository
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.screen.MainScreenDirections
import com.example.uzummarketclient.presenter.viewModel.HomeVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVMImpl @Inject constructor(
    private val homeRepository: HomeRepository,
    private val appNavigator: AppNavigator
) : ViewModel(), HomeVM {
    override val allCategoryByProductData = MutableStateFlow<List<CategoryByProductData>>(
        arrayListOf()
    )
    private var _errorMessage: ((String) -> Unit)? = null
    override val errorMessage = channelFlow {
        _errorMessage = {
            trySend(it)
        }
        awaitClose { _errorMessage = null }
    }
    override val progressState = MutableStateFlow(true)

    override fun onClickSearch() {
        homeRepository.setAllCategoryByProductsData(allCategoryByProductData.value)
    }

    override fun onClickCategory(category: CategoryByProductData) {
        homeRepository.setCategory(category)
    }

    override fun onClickProduct(product: ProductByMainData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToProductInfoScreen(product))
        }
    }

    override fun loadProducts() {
        progressState.value = false
        homeRepository.getAllCategoryByProduct().onEach {
            progressState.value = true
            val list = ArrayList<CategoryByProductData>()
            it.onSuccess { pairs ->
                val size = pairs.size
                var index = 0
                pairs.forEach {
                    index++
                    homeRepository.getProductInCategory(it.first, it.second).onEach { prod ->
                        list.add(CategoryByProductData(it.first, it.second, prod))
                        if (size == index) {
                            allCategoryByProductData.value = list
                        }
                    }.launchIn(viewModelScope)
                }
            }
            it.onFailure {
                _errorMessage?.invoke(it.message!!)
            }
        }.launchIn(viewModelScope)
    }
}
package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.model.BasketData
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.data.model.ProductData
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.domain.BasketRepository
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.screen.MainScreenDirections
import com.example.uzummarketclient.presenter.viewModel.BasketVM
import com.example.uzummarketclient.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketVMImpl @Inject constructor(
    private val repository: BasketRepository,
    private val appNavigator: AppNavigator,
) : ViewModel(), BasketVM {
    private val shared = MyShar
    override val successSetOrderFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorSetOrderFlow = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    override fun loadProducts(): Flow<Pair<ArrayList<ProductData>, List<Int>>> = callbackFlow {

        val basket: BasketData = if (!shared.isHasBasket()) {
            BasketData(arrayListOf(), arrayListOf())
        } else {
            shared.getBasket()
        }

        repository.getProducts(basket.productsId).onEach {
            trySend(Pair(it, basket.countsArr))
            channel.close()
        }.launchIn(viewModelScope)
        awaitClose()
    }

    override fun clickProduct(data: ProductData) {
        viewModelScope.launch {
            appNavigator.navigateTo(
                MainScreenDirections.actionMainScreenToProductInfoScreen(
                    ProductByMainData(
                        data.id,
                        data.images,
                        data.category,
                        data.description,
                        data.productName,
                        data.sellerName,
                        data.cost
                    )
                )
            )
        }
    }

    override fun deleteProduct(productId: String) {
        "${shared.deleteProductInBasket(productId)} delete product in basket".myLog()
    }

    override fun setOrder(balance: Int) {
        "orderga keldi hehe".myLog()
        repository.setBasket(balance).onEach {
            "Salom dunyo".myLog()
            it.onFailure {
                errorSetOrderFlow.tryEmit(it.message ?: "Unknown error!")
            }
            it.onSuccess {
                successSetOrderFlow.tryEmit(Unit)
                shared.deleteBasket()
            }
        }.launchIn(viewModelScope)
    }

    override fun onClickOrder() {
        viewModelScope.launch {
            appNavigator.navigateTo(R.id.action_mainScreen_to_myOrders)
        }
    }

}
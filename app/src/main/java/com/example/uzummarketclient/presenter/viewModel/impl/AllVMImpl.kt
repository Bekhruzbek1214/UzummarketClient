package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.data.model.MyOrdersData
import com.example.uzummarketclient.domain.OrdersRepository
import com.example.uzummarketclient.domain.impl.OrdersRepositoryImpl
import com.example.uzummarketclient.presenter.viewModel.AllVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AllVMImpl @Inject constructor():ViewModel(),AllVM{
    private val repository: OrdersRepository = OrdersRepositoryImpl()
    override fun getMyOrders(): Flow<List<MyOrdersData>> = callbackFlow {
        val data=ArrayList<MyOrdersData>()
        val balanse=ArrayList<Int>()
        repository.getAllMyOrder().onEach {
            val size=it.size
            var index=0
            it.forEach {
                balanse.add(it.first)
                repository.getProductName(it.second).onEach {
                    data.add(MyOrdersData(it,balanse[index]))
                    index++
                    if (index==size){
                        trySend(data)
                    }
                }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
        awaitClose()
    }

}
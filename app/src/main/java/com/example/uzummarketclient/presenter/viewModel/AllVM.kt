package com.example.uzummarketclient.presenter.viewModel

import com.example.uzummarketclient.data.model.MyOrdersData
import kotlinx.coroutines.flow.Flow

interface AllVM {
    fun getMyOrders(): Flow<List<MyOrdersData>>
}
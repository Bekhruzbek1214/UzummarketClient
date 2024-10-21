package com.example.uzummarketclient.presenter.viewModel

import com.example.uzummarketclient.data.model.UserData

interface ProfileVM {
    var userInfo:((user:UserData)->Unit)?
    fun loadUserInfo()

    fun clickMyOrders()
}
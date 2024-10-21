package com.example.uzummarketclient.presenter.viewModel.impl

import com.example.uzummarketclient.data.model.UserData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.screen.MainScreenDirections
import com.example.uzummarketclient.presenter.viewModel.ProfileVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVMImpl @Inject constructor(
    private val appNavigator: AppNavigator
):ViewModel(),ProfileVM {
    override var userInfo: ((user: UserData) -> Unit)? =null

    override fun loadUserInfo() {
        userInfo?.invoke(MyShar.getUserData())
    }

    override fun clickMyOrders() {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToMyOrders())
        }
    }
}



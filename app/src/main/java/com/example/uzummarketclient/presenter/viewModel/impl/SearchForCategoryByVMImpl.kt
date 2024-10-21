package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import com.example.uzummarketclient.domain.HomeRepository
import com.example.uzummarketclient.navigation.AppNavigator
import com.example.uzummarketclient.presenter.viewModel.SearchForCategoryByVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchForCategoryByVMImpl @Inject constructor(
    appNavigator: AppNavigator,
    homeRepository: HomeRepository
) : ViewModel(), SearchForCategoryByVM {
}
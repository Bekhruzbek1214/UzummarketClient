package com.example.uzummarketclient.di

import com.example.uzummarketclient.domain.BasketRepository
import com.example.uzummarketclient.domain.CategoryByPrRepository
import com.example.uzummarketclient.domain.HomeRepository
import com.example.uzummarketclient.domain.LoginRepository
import com.example.uzummarketclient.domain.OrdersRepository
import com.example.uzummarketclient.domain.impl.BasketRepositoryImpl
import com.example.uzummarketclient.domain.impl.CategoryByPrRepositoryImpl
import com.example.uzummarketclient.domain.impl.HomeRepositoryImpl
import com.example.uzummarketclient.domain.impl.LoginRepositoryImpl
import com.example.uzummarketclient.domain.impl.OrdersRepositoryImpl
import com.example.uzummarketclient.presenter.viewModel.BasketVM
import com.example.uzummarketclient.presenter.viewModel.impl.BasketVMImpl
import com.example.uzummarketclient.presenter.viewModel.impl.LoginVMImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModel {
    @[Binds Singleton]
    fun bindLogin(impl:LoginRepositoryImpl):LoginRepository

    @[Binds Singleton]
    fun bindHomeScreen(impl : HomeRepositoryImpl) : HomeRepository

    @[Binds Singleton]
    fun bindCategoryByProduct(impl: CategoryByPrRepositoryImpl) : CategoryByPrRepository
    @[Binds Singleton]
    fun bindBasketScreen(impl : BasketRepositoryImpl) : BasketRepository

    @[Binds Singleton]
    fun bindOrderScreen(impl : OrdersRepositoryImpl) : OrdersRepository
}
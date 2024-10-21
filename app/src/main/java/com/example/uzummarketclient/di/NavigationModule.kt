package com.example.uzummarketclient.di

import com.example.uzummarketclient.navigation.AppNavigationDispatcher
import com.example.uzummarketclient.navigation.AppNavigationHandler
import com.example.uzummarketclient.navigation.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindAppNavigator(impl : AppNavigationDispatcher) : AppNavigator

    @[Binds Singleton]
    fun bindAppNavigationHandler(impl: AppNavigationDispatcher) : AppNavigationHandler
}
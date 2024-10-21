package com.example.uzummarketclient.presenter.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzummarketclient.presenter.screen.BasketScreen
import com.example.uzummarketclient.presenter.screen.HomeScreen
import com.example.uzummarketclient.presenter.screen.ProfileScreen

class MainScreenAdapter(fm: FragmentManager, lifecycle: Lifecycle,
                        val home: HomeScreen,
                        val backed:BasketScreen,
                        val profile: ProfileScreen
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> home
        1 -> backed
        else -> profile
    }
}
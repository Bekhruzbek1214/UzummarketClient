package com.example.uzummarketclient.presenter.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzummarketclient.presenter.screen.ActiveOrders
import com.example.uzummarketclient.presenter.screen.AllOrders

class PageAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val activeOrders: ActiveOrders,
    val allOrders: AllOrders
): FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
       return if(position == 0){
            activeOrders
        }else{
            allOrders
        }
    }

}
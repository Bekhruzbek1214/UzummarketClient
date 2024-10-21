package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenMyOrdersBinding
import com.example.uzummarketclient.presenter.adapters.PageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrders : Fragment(R.layout.screen_my_orders) {
    private val binding by viewBinding(ScreenMyOrdersBinding::bind)
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: PageAdapter
    private val activeOrders= ActiveOrders()
    private val allOrders= AllOrders()
    private val tabItem = arrayListOf("Active","All")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        adapter = PageAdapter(childFragmentManager, lifecycle,activeOrders,allOrders)
        viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, pos ->
            tab.text = tabItem[pos]
        }.attach()
    }



}
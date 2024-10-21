package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenActiveOrdersBinding
import com.example.uzummarketclient.presenter.adapters.MyOrdersAdapter
//import com.example.uzummarketclient.presenter.screen.my_orders.adapter.OuterOrderProductAdapter
import com.example.uzummarketclient.presenter.viewModel.impl.ActiveOrderVMImpl
import com.example.uzummarketclient.presenter.viewModel.ActiveVM
import com.example.uzummarketclient.utils.myLog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ActiveOrders : Fragment(R.layout.screen_active_orders) {
    private val binding by viewBinding(ScreenActiveOrdersBinding::bind)
    private val viewModel: ActiveVM by viewModels<ActiveOrderVMImpl>()
    private val adapter by lazy { MyOrdersAdapter() }
//    private val adapter = OuterOrderProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.activeOrdersList.adapter = adapter
        binding.activeOrdersList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getMyOrders().onEach {
            "Salom".myLog()
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}
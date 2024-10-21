package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenAllOrdersBinding
import com.example.uzummarketclient.presenter.adapters.MyOrdersAdapter
import com.example.uzummarketclient.presenter.viewModel.AllVM
import com.example.uzummarketclient.presenter.viewModel.impl.AllVMImpl
import com.example.uzummarketclient.utils.myLog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AllOrders : Fragment(R.layout.screen_all_orders) {
    private val binding by viewBinding(ScreenAllOrdersBinding::bind)
  private val viewModel: AllVM by viewModels<AllVMImpl>()
    private val adapter by lazy { MyOrdersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allOrdersList.adapter = adapter
        binding.allOrdersList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getMyOrders().onEach {
            "Salom".myLog()
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}
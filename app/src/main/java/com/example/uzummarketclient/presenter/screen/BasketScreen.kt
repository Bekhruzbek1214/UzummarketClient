package com.example.uzummarketclient.presenter.screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.model.ProductData
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.databinding.ScreenBasketBinding
import com.example.uzummarketclient.presenter.adapters.BasketAdapter
import com.example.uzummarketclient.presenter.viewModel.BasketVM
import com.example.uzummarketclient.presenter.viewModel.impl.BasketVMImpl
import com.example.uzummarketclient.utils.myShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BasketScreen : Fragment(R.layout.screen_basket) {
    private val binding by viewBinding(ScreenBasketBinding::bind)
    private val adapter = BasketAdapter()
    private val viewModel: BasketVM by viewModels<BasketVMImpl>()
    var list: ArrayList<ProductData>? = null
    private var isEmpty = true
    private var balance = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViewModel()

        binding.openSearch.setOnClickListener {
            binding.baskedTxt.visibility = View.GONE
            binding.searchWord.visibility = View.VISIBLE
            binding.openSearch.visibility = View.GONE
        }

        visibility()


        adapter.totalSummLiveData.observe(viewLifecycleOwner) {
            binding.totalPrice.text = "$it so'm"
        }

        adapter.counterLiveData.observe(viewLifecycleOwner) {
            binding.countProduct.text = "$it ta mahsulot"
        }

        adapter.itemClickListener {
            viewModel.clickProduct(it)
        }

    }


    private fun visibility() {
        if (isEmpty) {
            binding.cardBottom.visibility = View.GONE
            binding.totalPrice.visibility = View.GONE
            binding.order.visibility = View.GONE
            binding.countProduct.visibility = View.GONE
            binding.placeholder.visibility = View.VISIBLE
        } else {
            binding.cardBottom.visibility = View.VISIBLE
            binding.totalPrice.visibility = View.VISIBLE
            binding.order.visibility = View.VISIBLE
            binding.countProduct.visibility = View.VISIBLE
            binding.placeholder.visibility = View.GONE

        }

        binding.baskedTxt.visibility = View.VISIBLE
        binding.searchWord.visibility = View.GONE
        binding.openSearch.visibility = View.VISIBLE
    }

    private fun initViewModel() {
        viewModel.loadProducts().onEach {
            list = it.first
            adapter.setList(it.first)
            isEmpty = it.first.isEmpty()
            visibility()
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
        viewModel.successSetOrderFlow.onEach {
            binding.order.isClickable = true
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
        viewModel.errorSetOrderFlow.onEach {
            binding.order.isClickable = true
            it.myShortToast(requireContext())
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun initAdapter() {
        binding.basketRv.adapter = adapter
        binding.basketRv.layoutManager = LinearLayoutManager(requireContext())
        adapter.onClickDelete = { pos, id ->
            viewModel.deleteProduct(id)
            list?.removeAt(pos)
            adapter.notifyItemRemoved(pos)
            isEmpty = list?.isEmpty() ?: false
            visibility()
//            viewModel.loadProducts().onEach {
//                adapter.setList(it.first)
//                isEmpty = it.first.isEmpty()
//                visibility()
//            }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
        }
        if (MyShar.isHasBasket()) {
            binding.order.setOnClickListener {
                viewModel.setOrder(2000)
                binding.order.isClickable = false
                viewModel.onClickOrder()
            }
        } else {
            binding.order.setBackgroundResource(R.drawable.bg_order2)
            binding.order.setTextColor(Color.BLACK)
            binding.order.text = "Empty Basket"
        }
    }

}
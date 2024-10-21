package com.example.uzummarketclient.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.databinding.ItemInnerListProductBinding
import com.example.uzummarketclient.utils.myLog

class HomeScreenAdapter : ListAdapter<CategoryByProductData, HomeScreenAdapter.OuterHolder>(
    OuterDiffUtil
) {

    val favouriteLiveData = MutableLiveData<List<CategoryByProductData>>()

    private var onClickProduct: ((ProductByMainData) -> Unit)? = null
    private var onClickCategory: ((CategoryByProductData) -> Unit)? = null
    private var time = System.currentTimeMillis()

    inner class OuterHolder(val binding: ItemInnerListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val adapter = HomeScreenInnerAdapter()

        init {
            binding.btnAll.setOnClickListener {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    onClickCategory?.invoke(getItem(adapterPosition))
                }
            }
            adapter.setOnClickProduct {
                onClickProduct?.invoke(it)
            }
        }

        init {
            binding.rvListInner.adapter = adapter
            binding.rvListInner.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun bind() {
            binding.apply {
                " bu Adapter".myLog()
                categoryName.text = getItem(adapterPosition).categoryName
                adapter.submitList(getItem(adapterPosition).productsList)
            }
        }
    }

    object OuterDiffUtil : DiffUtil.ItemCallback<CategoryByProductData>() {
        override fun areItemsTheSame(
            oldItem: CategoryByProductData,
            newItem: CategoryByProductData
        ): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

        override fun areContentsTheSame(
            oldItem: CategoryByProductData,
            newItem: CategoryByProductData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterHolder {
        return OuterHolder(
            ItemInnerListProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OuterHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickProduct(onClickProduct: ((ProductByMainData) -> Unit)) {
        this.onClickProduct = onClickProduct
    }

    fun setOnClickCategory(onClickCategory: ((CategoryByProductData) -> Unit)) {
        this.onClickCategory = onClickCategory
    }

}
package com.example.uzummarketclient.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzummarketclient.data.model.MyOrdersData
import com.example.uzummarketclient.databinding.ItemOrderProductBinding

class MyOrdersAdapter: ListAdapter<MyOrdersData, MyOrdersAdapter.BasketViewHolder>(BasketDiffUtil) {


    object BasketDiffUtil : DiffUtil.ItemCallback<MyOrdersData>() {
        override fun areItemsTheSame(oldItem: MyOrdersData, newItem: MyOrdersData) =
            oldItem.productsName == newItem.productsName

        override fun areContentsTheSame(oldItem: MyOrdersData, newItem: MyOrdersData) =
            oldItem == newItem
    }

    inner class BasketViewHolder(private val binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.apply {
                var names=""
                for (i in getItem(adapterPosition).productsName){
                    names="$names$i, "
                }

                productsName.text=names.substring(0,names.length-1)
                costProduct.text="Total balance: ${getItem(adapterPosition).totalBalanse} so'm"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(
            ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind()
    }

}
package com.example.uzummarketclient.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.databinding.ItemProductBinding

class SearchForMainAdapter :
    ListAdapter<ProductByMainData, SearchForMainAdapter.SearchHolder>(SearchDiffUtil) {

    private var onClickProduct: ((ProductByMainData) -> Unit)? = null
    private var time = System.currentTimeMillis()

    inner class SearchHolder(private val binding: ItemProductBinding) :
        ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    onClickProduct?.invoke(getItem(adapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.apply {
                val item = getItem(adapterPosition)
                txtName.text = item.productName
                price.text = numberFormat(item.cost) + " so'm"
                val muddat = item.cost.toString().toInt() / 12
                txtTolovMuddat.text = numberFormat(muddat.toString()) + " so'm/12 oyiga"
                Glide.with(binding.root.context)
                    .load(item.images!!.get(0)!!)
                    .centerCrop()
                    .placeholder(R.drawable.ic_uzum_splash)
                    .error(R.drawable.ic_uzum_splash)
                    .into(binding.imgProduct)
            }
        }
    }


    object SearchDiffUtil : DiffUtil.ItemCallback<ProductByMainData>() {
        override fun areItemsTheSame(
            oldItem: ProductByMainData,
            newItem: ProductByMainData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductByMainData,
            newItem: ProductByMainData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        return SearchHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickProduct(onClickProduct: (ProductByMainData) -> Unit) {
        this.onClickProduct = onClickProduct
    }

    private fun numberFormat(number: String?): String {
        if (number == null) return ""
        val num = number.toString().toLongOrNull() ?: return ""
        return String.format("%,d", num).replace(",", " ")
    }
}
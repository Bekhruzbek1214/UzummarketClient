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

class HomeScreenInnerAdapter :
    ListAdapter<ProductByMainData, HomeScreenInnerAdapter.InnerHolder>(InnerDiffUtil) {
    private var onClickProduct : ((ProductByMainData) -> Unit)? = null
    private var time  = System.currentTimeMillis()
    inner class InnerHolder(private val binding: ItemProductBinding) :
        ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                if(System.currentTimeMillis() - time >= 500) {
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

    /**
    Glide
    .with(requireContext())
    .load("https://firebasestorage.googleapis.com/v0/b/chatappb7.appspot.com/o/luffy-laughing-one-5120x2880-12358.png?alt=media&token=75107024-806b-4d78-85a5-e6148a98ade5")
    .centerCrop()
    .placeholder(R.drawable.shape1)
    .error(R.drawable.shape2)
    .into(binding.imageLuffy)
     */

    object InnerDiffUtil : DiffUtil.ItemCallback<ProductByMainData>() {
        override fun areItemsTheSame(oldItem: ProductByMainData, newItem: ProductByMainData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductByMainData, newItem: ProductByMainData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return InnerHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.bind()
    }
    fun setOnClickProduct(onClickProduct : (ProductByMainData) -> Unit) {
        this.onClickProduct = onClickProduct
    }
    private fun numberFormat(number : String?) : String{
        if(number == null) return ""
        val num = number.toString().toLongOrNull() ?: return ""
        return String.format("%,d", num).replace(",", " ")
    }
}
package com.example.uzummarketclient.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.model.ProductData
import com.example.uzummarketclient.databinding.ItemBasketBinding

class BasketAdapter : ListAdapter<ProductData, BasketAdapter.BasketViewHolder>(BasketDiffUtil) {

    private var allSumm = 0
    private var counter = 0

    val totalSummLiveData = MutableLiveData<Int>()
    val counterLiveData = MutableLiveData<Int>()
    var onClickDelete:((Int,String)->Unit)?=null

    private var selectedItemListener: ((ProductData) -> Unit)? = null

    val list = ArrayList<Int>(itemCount)


    object BasketDiffUtil : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData) =
            oldItem == newItem
    }

    inner class BasketViewHolder(private val binding: ItemBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                selectedItemListener?.invoke(currentList[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(getItem(adapterPosition).images!!.get(0)!!)
                    .centerCrop()
                    .placeholder(R.drawable.ic_uzum_splash)
                    .error(R.drawable.ic_uzum_splash)
                    .into(binding.img)
                aboutProduct.text =
                    getItem(adapterPosition).productName + "\n" + getItem(adapterPosition).description
                sellerName.text = getItem(adapterPosition).sellerName
                price.text = getItem(adapterPosition).cost + " so'm"
                productCount.text = (list[adapterPosition]).toString()
                allSumm += getItem(adapterPosition).cost!!.toInt()
                counter++
                totalSummLiveData.value = allSumm
                counterLiveData.value = counter


                plus.setOnClickListener {
                    productCount.text = (++list[adapterPosition]).toString()
                    allSumm += getItem(adapterPosition).cost!!.toInt()
                    counter++
                    totalSummLiveData.value = allSumm
                    counterLiveData.value = counter
                }
                minus.setOnClickListener {
                    if (list[adapterPosition] == 0) return@setOnClickListener
                    productCount.text = (--list[adapterPosition]).toString()
                    allSumm -= getItem(adapterPosition).cost!!.toInt()
                    counter--
                    totalSummLiveData.value = allSumm
                    counterLiveData.value = counter
                }
                delete.setOnClickListener {
                    onClickDelete?.invoke(adapterPosition,getItem(adapterPosition).id)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(
            ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind()
    }

    fun setList(data: List<ProductData>) {
        for (i in 0..<data.size) {
            list.add(1)
        }
        submitList(data)
    }

    fun itemClickListener(block: (ProductData) -> Unit) {
        this.selectedItemListener = block
    }
}
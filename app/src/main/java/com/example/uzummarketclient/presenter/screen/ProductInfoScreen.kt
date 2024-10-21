package com.example.uzummarketclient.presenter.screen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denzcoskun.imageslider.models.SlideModel
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenProductInfoBinding
import com.example.uzummarketclient.presenter.viewModel.ProductInfoVM
import com.example.uzummarketclient.presenter.viewModel.impl.ProductInfoVMImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductInfoScreen : Fragment(R.layout.screen_product_info) {

    private val binding by viewBinding(ScreenProductInfoBinding::bind)
    private val viewModel: ProductInfoVM by viewModels<ProductInfoVMImpl>()
    private val navArgs: ProductInfoScreenArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.backScreen.setOnClickListener {
            viewModel.backScreen()
        }

//       binding.dotsIndicator.setDot

        binding.productName.text = navArgs.data.productName
        binding.productInfo.text = navArgs.data.description
        binding.totalPrice.text = "${navArgs.data.cost} so'm"
        if (navArgs.data.images!!.isEmpty()) {
//            binding.productImage.setImageResource(R.drawable.ic_uzum_splash)
        } else {

            val imageList = ArrayList<SlideModel>()
            for (i in 0 until navArgs.data.images!!.size)
                imageList.add(SlideModel(navArgs.data.images!![i]))

//            Glide.with(binding.root.context)
//                .load(navArgs.data.images!![0])
//                .centerCrop()
//                .placeholder(R.drawable.ic_uzum_splash)
//                .error(R.drawable.ic_uzum_splash)
//                .into(binding.productImage)


            binding.productImage.setImageList(imageList)
        }
        if (viewModel.isHaveProductInBasked(navArgs.data.id)) {
            binding.order.isClickable = false
            binding.order.setBackgroundResource(R.drawable.bg_order2)
            binding.order.text = "Savatga qoshilgan"
            binding.order.setTextColor(Color.BLACK)
        } else {
            binding.order.setOnClickListener {
                viewModel.setProductInBasket(navArgs.data.id)
                binding.order.isClickable = false
                binding.order.setBackgroundResource(R.drawable.bg_order2)
                binding.order.text = "Savatga qoshildi"
                binding.order.setTextColor(Color.BLACK)
            }
        }


    }


}
package com.example.uzummarketclient.presenter.screen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenProfileBinding
import com.example.uzummarketclient.presenter.viewModel.ProfileVM
import com.example.uzummarketclient.presenter.viewModel.impl.ProfileVMImpl
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileScreen:Fragment(R.layout.screen_profile) {
    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val viewModel:ProfileVM by viewModels<ProfileVMImpl>()
    var onClickLogOut:(()->Unit)?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initButtons()
        viewModel.loadUserInfo()

        binding.buyurtma.setOnClickListener{
            viewModel.clickMyOrders()
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window: Window = activity?.window ?: return
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.statusBarColor = Color.TRANSPARENT
//        }
    }


    private fun initButtons(){
        binding.apply {
            binding.logOut.setOnClickListener {
                onClickLogOut?.invoke()
            }
        }
    }

    private fun initViewModel(){
        viewModel.userInfo={
            binding.userName.text=it.name
            binding.userGmail.text=it.gmail
        }
    }

}
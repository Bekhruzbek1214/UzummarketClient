package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.databinding.ScreenMainBinding
import com.example.uzummarketclient.presenter.adapters.MainScreenAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private lateinit var adapter: MainScreenAdapter
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val homeScreen= HomeScreen()
    private val basketScreen=BasketScreen()
    private val profileScreen=ProfileScreen()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initButton()

    }
    fun initAdapter(){
        adapter = MainScreenAdapter(childFragmentManager, lifecycle,homeScreen,basketScreen,profileScreen)

        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu[position].isChecked = true
            }
        })
        binding.viewPager.isUserInputEnabled = false
    }
    fun initButton(){
        binding.apply {
            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeScreen -> binding.viewPager.currentItem = 0
                    R.id.basketScreen -> binding.viewPager.currentItem = 1
                    else -> binding.viewPager.currentItem = 2
                }
                return@setOnItemSelectedListener true
            }
            profileScreen.onClickLogOut={
                MyShar.logOut()
                findNavController().navigate(R.id.action_mainScreen_to_loginScreen)
            }
        }
    }

}
package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.databinding.ScreenSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = MyShar

        binding.splashImg.animate()
            .rotationY(360f)
            .setDuration(900)

        lifecycleScope.launch {
            delay(1000)

            if (sharedPref.isLogin()) {
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
            } else findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginScreen())
        }
    }


}
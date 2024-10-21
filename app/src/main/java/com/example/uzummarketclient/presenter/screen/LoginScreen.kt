package com.example.uzummarketclient.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.uzummarketclient.R
import com.example.uzummarketclient.databinding.ScreenLogInBinding
import com.example.uzummarketclient.presenter.viewModel.LoginVM
import com.example.uzummarketclient.presenter.viewModel.impl.LoginVMImpl
import com.example.uzummarketclient.utils.myShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_log_in) {
    private val binding by viewBinding(ScreenLogInBinding::bind)
    private val viewModel: LoginVM by viewModels<LoginVMImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initButton()
    }

    private fun initButton() {
        binding.apply {
            loginButton.setOnClickListener {
//                if (!isValidPassword())return@setOnClickListener
//                if (!isValidGmail())return@setOnClickListener
                viewModel.loginUser(passwordEditText.text.toString(), emailEditText.text.toString())
            }
        }
    }

    private fun isValidGmail(): Boolean {
        val text = (binding.emailEditText.text?.isEmpty() ?: "").toString()
        if (text == "") return false
        return !(!text.contains("@") && !text.contains(".com"))
    }

    private fun isValidPassword(): Boolean {
        val text = (binding.passwordEditText.text?.isEmpty() ?: "").toString()
        return text.length == 8
    }

    private fun initViewModel() {
        viewModel.apply {
            successLoginFlow.onEach {
                findNavController().navigate(R.id.action_loginScreen_to_mainScreen)
            }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
            errorMessage.onEach {
                it.myShortToast(requireContext())
            }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
        }
    }


}
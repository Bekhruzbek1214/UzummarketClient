package com.example.uzummarketclient.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzummarketclient.domain.LoginRepository
import com.example.uzummarketclient.presenter.viewModel.LoginVM
import com.example.uzummarketclient.utils.myLog
import com.example.uzummarketclient.utils.myShortToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginVMImpl @Inject constructor(
    val repository:LoginRepository,
):ViewModel(), LoginVM {
    override val successLoginFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun loginUser(password: String, gmail: String){
        repository.loginUser(password,gmail).onEach {
            it.onSuccess {
                successLoginFlow.tryEmit(Unit)
            }
            it.onFailure {
                errorMessage.tryEmit(it.message?:"Unknown error")
            }
        }.launchIn(viewModelScope)
    }
}
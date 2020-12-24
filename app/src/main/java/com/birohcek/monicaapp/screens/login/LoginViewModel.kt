package com.birohcek.monicaapp.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birohcek.monicaapp.repository.AccountRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("ConvertSecondaryConstructorToPrimary")
class LoginViewModel : ViewModel {
    private val accountRepository: AccountRepository

    @ViewModelInject
    constructor(accountRepository: AccountRepository) : super() {
        this.accountRepository = accountRepository
    }

    data class LoginState(val loadingState: LoadingState, val error: String?, val data: Long?)

    fun onLogin(
        username: String,
        password: String,
        serverAddress: String) {
        viewModelScope.launch {
            accountRepository.addUser(username, password, serverAddress).collect {
                handleJobState(it)
            }
        }
    }

    private fun handleJobState(it: JobState) {
        _state.value = LoginState(it.jobState, message(it.error), it.accountId)
    }

    private fun message(error: Throwable?): String? {
        val error = error ?: return null
        return when(error) {
            is AccountRepository.UserAlreadyExistsException -> {
                "You already logged in with this user"
            }

            else -> error.message
        }
    }

    private val _state: MutableState<LoginState> = mutableStateOf(LoginState(LoadingState.IDLE, null, null))
    public val state get() = _state
}
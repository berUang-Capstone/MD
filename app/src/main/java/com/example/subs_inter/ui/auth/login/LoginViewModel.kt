package com.example.subs_inter.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.repository.ITokenRepository
import com.example.subs_inter.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenRepository: ITokenRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError = _isError

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    fun fetchLoginStatus() {
        viewModelScope.launch {
            tokenRepository.isLogin().collect {
                _isSuccess.value = it
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                when (it) {
                    is Resource.Error -> {
                        _isLoading.value = false
                        _isSuccess.value = false
                        _message.value = it.message ?: ""
                    }
                    Resource.Loading -> {
                        _isLoading.value = true
                        _isError.value = false
                        _isSuccess.value = false
                    }
                    is Resource.Success -> {
                        _isError.value = false
                        _isSuccess.value = true
                        _isLoading.value = false
                        _message.value = it.data!!.message
                    }
                }
            }
        }
    }
}
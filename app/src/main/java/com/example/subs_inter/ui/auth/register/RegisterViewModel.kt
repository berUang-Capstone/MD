package com.example.subs_inter.ui.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.domain.repository.IAuthRepository
import com.example.subs_inter.domain.repository.ITokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val tokenRepository: ITokenRepository,
    private val authRepository: IAuthRepository,

): ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    fun fetchLoginStatus() {
        viewModelScope.launch {
            tokenRepository.isLogin().collect {
                _isSuccess.value = it
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authRepository.register(email, password)
        }
    }
}
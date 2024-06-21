package com.example.subs_inter.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.domain.repository.IUserRepository
import com.example.subs_inter.domain.usecase.LogoutUseCase
import com.example.subs_inter.ui.auth.login.LoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
private val logoutUseCase: LogoutUseCase,

): ViewModel()  {

    fun logout(onDone: () -> Unit = {}) {
        viewModelScope.launch {
            logoutUseCase()
            delay(500)
            onDone()
        }
    }

}
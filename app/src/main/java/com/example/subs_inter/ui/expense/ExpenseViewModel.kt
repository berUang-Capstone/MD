package com.example.subs_inter.ui.expense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.wallet.WalletModel
import com.example.subs_inter.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val userRepository: IUserRepository
): ViewModel() {

    private val _wallet = MutableLiveData<WalletModel>()
    val wallet = _wallet

    fun fetchWallet() {
        viewModelScope.launch {
            userRepository.fetchWalelt().collect {
                when (it) {
                    is Resource.Error -> {

                    }
                    Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _wallet.value = it.data!!
                    }
                }
            }
        }
    }
}
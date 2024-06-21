package com.example.subs_inter.ui.home
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.wallet.WalletModel
import com.example.subs_inter.domain.repository.INoteRepository
import com.example.subs_inter.domain.repository.IUserRepository
import com.example.subs_inter.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
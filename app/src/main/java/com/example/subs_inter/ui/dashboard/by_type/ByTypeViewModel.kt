package com.example.subs_inter.ui.dashboard.by_type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.transaction.TrxModel
import com.example.subs_inter.domain.repository.INoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ByTypeViewModel @Inject constructor(
    private val noteRepository: INoteRepository
): ViewModel() {
    private val _trxDatas = MutableLiveData<TrxModel>()
    val trxDatas = _trxDatas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading


    fun fetchByType(type: String) {
        viewModelScope.launch {
            noteRepository.fetchTransactionByType(type).collect {
                when(it) {
                    is Resource.Error -> {
                        _isLoading.value = false
                    }
                    Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _trxDatas.value = it.data!!
                    }
                }
            }
        }
    }
}
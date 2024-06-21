package com.example.subs_inter.ui.dashboard.all_trx

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.transaction.TrxHistoryModel
import com.example.subs_inter.domain.model.transaction.TrxModel
import com.example.subs_inter.domain.repository.INoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTrxViewModel @Inject constructor(
    private val noteRepository: INoteRepository
): ViewModel() {
    private val _trxHistory = MutableLiveData<TrxHistoryModel>()
    val trxHistory = _trxHistory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun fetchHistory() {
        viewModelScope.launch {
            noteRepository.fetchTrxHistory().collect {
                when(it) {
                    is Resource.Error -> {
                        _isLoading.value = false
                    }
                    Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _trxHistory.value = it.data!!
                    }
                }
            }
        }
    }
}
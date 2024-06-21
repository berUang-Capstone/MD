package com.example.subs_inter.ui.upload

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.transaction.TrxModel
import com.example.subs_inter.domain.usecase.ScanReceiptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakeImageViewModel @Inject constructor(
    private val scanReceiptUseCase: ScanReceiptUseCase
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _noteCategory = MutableLiveData<String>()
    val noteCategory: LiveData<String> = _noteCategory

    private var isScanning = false

    fun scanTheImage(imageUri: String, context: Context) {
        if (!isScanning) {
            isScanning = true
            _isLoading.value = true  // Start loading
            viewModelScope.launch {
                scanReceiptUseCase(imageUri, context).collect {
                    when (it) {
                        is Resource.Loading -> _isLoading.value = true
                        is Resource.Success -> {
                            _isLoading.value = false
                            _isSuccess.value = true
                            _noteCategory.value = it.data?.transaction?.firstOrNull()?.category ?: ""
                            isScanning = false
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _message.value = it.message  // Handle error message
                            isScanning = false
                        }
                    }
                }
            }
        }
    }
}

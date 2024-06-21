package com.example.subs_inter.ui.summary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.repository.INoteRepository
import com.example.subs_inter.domain.usecase.UploadNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val noteRepository: INoteRepository,
    private val uploadNoteUseCase: UploadNoteUseCase
): ViewModel() {

    private val _notes = MutableLiveData<List<NoteModel>>()
    val notes = _notes

    val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun uploadData(category: String, notes: List<NoteModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadNoteUseCase(category = category, notes = notes).collect {
                when (it) {
                    is Resource.Error -> {
                    }
                    Resource.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Resource.Success -> {
                        _isLoading.postValue(false)
                        _isSuccess.postValue(true)
                    }
                }
            }
        }
    }

    fun deleteData(note: NoteModel) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun fetchNotesByCategory(category: String) {
        viewModelScope.launch {
            noteRepository.getNotesByCategory(category).collect {
                when (it) {
                    is Resource.Error -> {
                        _isSuccess.value = false

                    }
                    is Resource.Loading -> {
                        _isSuccess.value = false
                    }
                    is Resource.Success -> {
                        _notes.value = it.data!!
                    }
                }
            }
        }
    }
}
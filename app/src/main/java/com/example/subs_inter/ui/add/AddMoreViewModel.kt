package com.example.subs_inter.ui.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.repository.INoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMoreViewModel @Inject constructor(
    private val repository: INoteRepository
) : ViewModel(){

    private val _notes = MutableLiveData<List<NoteModel>>()
    val notes = _notes

    fun getAllNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect {
                when (it) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _notes.value = it.data!!
                    }
                }
            }
        }
    }
}
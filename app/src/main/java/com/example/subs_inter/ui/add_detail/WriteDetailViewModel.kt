package com.example.subs_inter.ui.add_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.repository.INoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteDetailViewModel @Inject constructor(
    private val noteRepository: INoteRepository
) : ViewModel() {
    private val _note = MutableLiveData<NoteModel>()
    val note = _note

    init {
        setBlankNote()
    }

    private fun setBlankNote() {
        _note.value = NoteModel(
            title = "",
            category = "",
            item = "",
            amount = "",
            quantity = "",
            date = ""
        )
    }

    fun setNote(note: NoteModel) {
        _note.value = note
        Log.d("WDVM", note.toString())
    }

    // Notes Type and Category
    fun updateTitleCategoryNote(type: String, category: String) {
        _note.value = _note.value?.copy(
            item = type,
            category = category
        )
    }


    fun insert() {
        viewModelScope.launch {
            if (note.value != null) {
                noteRepository.insertNote(note.value!!)
            }
        }
    }
    fun update(note: NoteModel) {
        viewModelScope.launch {
            noteRepository.update(note)
        }
    }

    fun delete(note: NoteModel) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}
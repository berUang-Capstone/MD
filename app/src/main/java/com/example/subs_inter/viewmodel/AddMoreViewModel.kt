package com.example.subs_inter.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.subs_inter.database.Note
import com.example.subs_inter.database.NoteRepository

class AddMoreViewModel (application: Application) : ViewModel(){
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()

}
package com.example.subs_inter.domain.repository

import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.model.transaction.TrxHistoryModel
import com.example.subs_inter.domain.model.transaction.TrxModel
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getAllNotes(): Flow<Resource<List<NoteModel>>>
    fun getNotesByCategory(category: String): Flow<Resource<List<NoteModel>>>
    suspend fun insertNote(note: NoteModel)
    suspend fun deleteNote(note: NoteModel)
    suspend fun update(note: NoteModel)
    suspend fun deleteNotesByCategory(category: String)

    fun uploadNote(note: NoteModel): Flow<Resource<String>>
    fun fetchTransactionByType(type: String): Flow<Resource<TrxModel>>
    fun fetchTransactionByCategory(category: String): Flow<Resource<TrxModel>>
    fun fetchTrxHistory(): Flow<Resource<TrxHistoryModel>>
    fun fetchTransactionByDate(startDate: String, endDate: String): Flow<Resource<TrxModel>>

    fun scanTheImage(image: ByteArray): Flow<Resource<TrxModel>>
}
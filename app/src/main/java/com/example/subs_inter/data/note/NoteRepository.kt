package com.example.subs_inter.data.note

import android.util.Log
import com.example.subs_inter.data.Resource
import com.example.subs_inter.data.note.mapper.NoteMapper.noteModelToEntity
import com.example.subs_inter.data.note.mapper.NoteMapper.noteModelToRequest
import com.example.subs_inter.data.note.mapper.NoteMapper.noteTrxHistoryResponseToModel
import com.example.subs_inter.data.note.mapper.NoteMapper.noteTrxResponse
import com.example.subs_inter.data.note.mapper.NoteMapper.notesEntityToModels
import com.example.subs_inter.data.note.mapper.NoteMapper.trxScanResponseToTrxModel
import com.example.subs_inter.data.note.source.local.NoteDao
import com.example.subs_inter.data.note.source.network.NoteApiService
import com.example.subs_inter.data.note.source.network.request.TrxByCategory
import com.example.subs_inter.data.note.source.network.request.TrxByDate
import com.example.subs_inter.data.note.source.network.request.TrxByType
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.model.transaction.TrxHistoryModel
import com.example.subs_inter.domain.model.transaction.TrxModel
import com.example.subs_inter.domain.repository.INoteRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApiService: NoteApiService
): INoteRepository {
    override fun getAllNotes(): Flow<Resource<List<NoteModel>>> = flow {
        try {
            val response = noteDao.getAllNotes().map {
                notesEntityToModels(it)
            }
            response.collect{
               emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getNotesByCategory(category: String): Flow<Resource<List<NoteModel>>> = noteDao.getNotesByCategory(category).map {
        Resource.Success(notesEntityToModels(it))
    }

    override suspend fun insertNote(note: NoteModel) {
        noteDao.insert(
            noteModelToEntity(note)
        )
    }

    override suspend fun deleteNote(note: NoteModel) {
        noteDao.delete(noteModelToEntity(note))
    }

    override suspend fun update(note: NoteModel) {
        noteDao.delete(noteModelToEntity(note))
    }

    override suspend fun deleteNotesByCategory(category: String) {
        noteDao.deleteAllByCategory(category)
    }

    override fun uploadNote(note: NoteModel): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        emit(try {
            val gson = Gson()
            val requestBodyRaw = noteModelToRequest(note)
            val requestBodyJson = gson.toJson(requestBodyRaw)
            val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())
            noteApiService.uploadTransaction(requestBody)
            Resource.Success("Success")
        } catch (e: Exception){
            Log.e("UploadError", e.message.toString())
            Resource.Error(e.message.toString())
        })
    }

    override fun fetchTransactionByType(type: String): Flow<Resource<TrxModel>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val gson = Gson()
                val requestBodyRaw = TrxByType(type)
                val requestBodyJson = gson.toJson(requestBodyRaw)
                val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())
                val response = noteApiService.fetchTransactionByType(requestBody)
                Resource.Success(noteTrxResponse(response))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }

    override fun fetchTransactionByCategory(category: String): Flow<Resource<TrxModel>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val gson = Gson()
                val requestBodyRaw = TrxByCategory(category)
                val requestBodyJson = gson.toJson(requestBodyRaw)
                val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())
                val response = noteApiService.fetchTransactionByCategory(requestBody)
                Resource.Success(noteTrxResponse(response))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }


    override fun fetchTrxHistory(): Flow<Resource<TrxHistoryModel>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val response = noteApiService.fetchHistory()
                Resource.Success(noteTrxHistoryResponseToModel(response))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }

    override fun fetchTransactionByDate(
        startDate: String,
        endDate: String
    ): Flow<Resource<TrxModel>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val gson = Gson()
                val requestBodyRaw = TrxByDate(startDate = startDate, endDate = endDate)
                val requestBodyJson = gson.toJson(requestBodyRaw)
                val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())
                val response = noteApiService.fetchTransactionByDate(requestBody)
                Resource.Success(noteTrxResponse(response))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }

    override fun scanTheImage(image: ByteArray): Flow<Resource<TrxModel>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val requestFile = image.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)
                val response = noteApiService.scanTheReceipt(body)
                Resource.Success(trxScanResponseToTrxModel(response))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }

}
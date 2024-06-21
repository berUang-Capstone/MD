package com.example.subs_inter.domain.usecase

import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadNoteUseCase @Inject constructor(
    private val noteRepository: INoteRepository,
) {
    suspend operator fun invoke(category: String, notes: List<NoteModel>): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        var allSuccess = true
        for (noteModel in notes) {
            val result = withContext(Dispatchers.IO) {
                var uploadResult: Resource<Boolean>? = null
                noteRepository.uploadNote(noteModel).collect {
                    if (it is Resource.Success) {
                        noteRepository.deleteNote(noteModel)
                        uploadResult = Resource.Success(true)
                    } else if (it is Resource.Error) {
                        uploadResult = Resource.Error("")
                    }
                }
                uploadResult
            }
            if (result is Resource.Error) {
                allSuccess = false
                emit(result)  // Emit error immediately and stop processing further
                return@flow
            }
        }
        if (allSuccess) {
            withContext(Dispatchers.IO) {
                noteRepository.deleteNotesByCategory(category = category)
            }
            emit(Resource.Success(true))
        }
    }.flowOn(Dispatchers.IO)
}
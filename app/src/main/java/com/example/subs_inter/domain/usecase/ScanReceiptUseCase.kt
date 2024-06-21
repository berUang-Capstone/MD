package com.example.subs_inter.domain.usecase

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.transaction.TrxMapper.mapTrxDataModelToNoteModel
import com.example.subs_inter.domain.model.transaction.TrxModel
import com.example.subs_inter.domain.repository.INoteRepository
import com.example.subs_inter.util.Utils.getImageByteArrayFromUri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScanReceiptUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(imageStringUri: String, context: Context): Flow<Resource<TrxModel>> {
        val imageByteArray = getImageByteArrayFromUri(imageStringUri.toUri(), context)
        if (imageByteArray != null) {
            val response = noteRepository.scanTheImage(imageByteArray)
            response.collect {
                if (it is Resource.Success) {
                    it.data?.transaction?.forEach {trxDataModel ->
                        noteRepository.insertNote(mapTrxDataModelToNoteModel(trxDataModel))
                    }
                }
            }
            return response
        } else {
            return flow {
                Resource.Error<TrxModel>("Image Null")
            }
        }
    }
}
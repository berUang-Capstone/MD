package com.example.subs_inter.data.note.mapper

import com.example.subs_inter.data.note.source.local.entity.NoteEntity
import com.example.subs_inter.data.note.source.network.request.CreateTrxBody
import com.example.subs_inter.data.note.source.network.response.TransactionDataResponse
import com.example.subs_inter.data.note.source.network.response.TrxHistoryResponse
import com.example.subs_inter.data.note.source.network.response.TrxResponse
import com.example.subs_inter.data.note.source.network.response.TrxScanResponse
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.domain.model.transaction.TransactionDataModel
import com.example.subs_inter.domain.model.transaction.TrxHistoryModel
import com.example.subs_inter.domain.model.transaction.TrxModel
import com.example.subs_inter.util.Utils.extractDate

object NoteMapper {
    private fun noteEntityToModel(q: NoteEntity): NoteModel = NoteModel(
        id = q.id, title = q.title ?: "", category = q.category ?: "",
        item = q.item ?: "", amount = q.amount ?: "", quantity = q.quantity ?: "", date = q.date ?: ""
    )

    fun noteModelToEntity(q: NoteModel): NoteEntity = NoteEntity(
        id = q.id, title = q.title, category = q.category,
        item = q.item, amount = q.amount, quantity = q.quantity, date = q.date
    )

    fun notesEntityToModels(q: List<NoteEntity>): List<NoteModel> = q.map {
        noteEntityToModel(it)
    }.toList()

    fun noteTrxDataResponseToModel(q: TransactionDataResponse) = TransactionDataModel(
        type = q.type, category = q.category, amount = q.amount, name = q.name, createdAt = extractDate(q.createdAt)
    )

    fun noteTrxHistoryResponseToModel(q: TrxHistoryResponse) = TrxHistoryModel(
        currentBalance = q.currentBalance, transactions = q.transactions.map {
            noteTrxDataResponseToModel(it)
        }
    )


    fun noteTrxResponse(q: TrxResponse) = TrxModel(
        totalAmount = q.totalAmount, transaction = q.transaction.map {
            noteTrxDataResponseToModel(it)

        }
    )
    
    fun noteModelToRequest(q: NoteModel) = CreateTrxBody(
        type = q.item, category = q.category, amount = q.amount.toDouble(), name = q.title
    )
    
    fun trxScanResponseToTrxModel(q: TrxScanResponse) = TrxModel(
        totalAmount = q.totalTransactions, transaction = q.transaction.map {
            noteTrxDataResponseToModel(it)
        }
    )
}
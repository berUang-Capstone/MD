package com.example.subs_inter.domain.model.transaction

import com.example.subs_inter.domain.model.NoteModel

object TrxMapper {
    fun mapTrxDataModelToNoteModel(q: TransactionDataModel) = NoteModel(
        title = q.name, category = q.category, item = q.type, amount = q.amount.toString(),
        quantity = 1.toString(), date = q.createdAt
    )
}
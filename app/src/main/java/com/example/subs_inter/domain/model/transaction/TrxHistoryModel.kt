package com.example.subs_inter.domain.model.transaction

data class TrxHistoryModel (
    val currentBalance: Int,
    val transactions: List<TransactionDataModel>
)
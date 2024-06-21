package com.example.subs_inter.domain.model.transaction

data class TrxModel(
    val totalAmount: Int,
    val transaction: List<TransactionDataModel>
)

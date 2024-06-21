package com.example.subs_inter.domain.model.transaction

data class TransactionDataModel(
    val type: String,
    val category: String,
    val amount: Int,
    val name: String,
    val createdAt: String
)

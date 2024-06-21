package com.example.subs_inter.data.note.source.network.request

data class CreateTrxBody(
    val type: String,
    val category: String,
    val amount: Double,
    val name: String
)

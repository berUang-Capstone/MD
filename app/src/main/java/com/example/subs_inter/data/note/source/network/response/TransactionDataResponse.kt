package com.example.subs_inter.data.note.source.network.response

import com.google.gson.annotations.SerializedName

data class TransactionDataResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("createdAt")
    val createdAt: String
)

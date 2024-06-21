package com.example.subs_inter.data.note.source.network.response

import com.google.gson.annotations.SerializedName

data class TrxHistoryResponse(
    @SerializedName("current_balance")
    val currentBalance: Int,
    @SerializedName("transactions")
    val transactions: List<TransactionDataResponse>
)

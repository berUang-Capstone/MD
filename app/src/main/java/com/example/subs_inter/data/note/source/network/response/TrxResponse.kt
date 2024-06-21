package com.example.subs_inter.data.note.source.network.response

import com.google.gson.annotations.SerializedName

data class TrxResponse(
    @SerializedName("total_amount")
    val totalAmount: Int,
    @SerializedName("transactions")
    val transaction: List<TransactionDataResponse>
)
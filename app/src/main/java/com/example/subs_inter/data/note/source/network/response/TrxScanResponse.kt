package com.example.subs_inter.data.note.source.network.response

import com.google.gson.annotations.SerializedName

data class TrxScanResponse(
    @SerializedName("total_transactions")
    val totalTransactions: Int,
    @SerializedName("transactions")
    val transaction: List<TransactionDataResponse>
)

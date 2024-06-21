package com.example.subs_inter.data.user.response

import com.google.gson.annotations.SerializedName

data class WalletResponse (
    @SerializedName("wallet")
    val wallet: WalletDataResponse
)
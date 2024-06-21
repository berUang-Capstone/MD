package com.example.subs_inter.data.user.response

import com.google.gson.annotations.SerializedName

data class WalletDataResponse(
    @SerializedName("salary")
    val salary: Int,

    @SerializedName("others")
    val others: Int,

    @SerializedName("balance")
    val balance: Int,

    @SerializedName("investment")
    val investment: Int,

    @SerializedName("shopping")
    val shopping: Int,

    @SerializedName("transportation")
    val transportation: Int,

    @SerializedName("food")
    val food: Int,

    @SerializedName("bonus")
    val bonus: Int,

    @SerializedName("expense")
    val expense: Int,

    @SerializedName("income")
    val income: Int
)
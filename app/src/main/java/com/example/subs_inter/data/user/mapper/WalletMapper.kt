package com.example.subs_inter.data.user.mapper

import com.example.subs_inter.data.user.response.WalletDataResponse
import com.example.subs_inter.domain.model.wallet.WalletModel

object WalletMapper {
    fun mapWalletResponseToModel(response: WalletDataResponse): WalletModel {
        return WalletModel(
            salary = response.salary,
            others = response.others,
            balance = response.balance,
            investment = response.investment,
            shopping = response.shopping,
            transportation = response.transportation,
            food = response.food,
            bonus = response.bonus,
            expense = response.expense,
            income = response.income
        )
    }
}
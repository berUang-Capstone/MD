package com.example.subs_inter.domain.repository

import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.wallet.WalletModel
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun fetchWalelt(): Flow<Resource<WalletModel>>
}
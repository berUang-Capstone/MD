package com.example.subs_inter.domain.repository

import kotlinx.coroutines.flow.Flow

interface ITokenRepository {
    suspend fun removeSession()
    fun isLogin(): Flow<Boolean>
    suspend fun login(email: String, name: String)
}
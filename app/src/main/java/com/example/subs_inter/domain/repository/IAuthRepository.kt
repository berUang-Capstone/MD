package com.example.subs_inter.domain.repository

import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.auth.LoginModel
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(email: String, password: String): Flow<Resource<LoginModel>>
    suspend fun register(email: String, password: String)
    suspend fun logout()
}
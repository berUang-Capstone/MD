package com.example.subs_inter.data.auth

import com.example.subs_inter.data.Resource
import com.example.subs_inter.data.auth.mapper.AuthMapper.loginResponseToModel
import com.example.subs_inter.data.auth.request.LoginRequest
import com.example.subs_inter.data.auth.request.RegisterRequest
import com.example.subs_inter.data.auth.source.AuthApiService
import com.example.subs_inter.domain.model.auth.LoginModel
import com.example.subs_inter.domain.repository.IAuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
): IAuthRepository {
    override fun login(email: String, password: String): Flow<Resource<LoginModel>> = flow {
        emit(Resource.Loading)
        emit(try {
            val gson = Gson()
            val requestBodyRaw = LoginRequest(email, password)
            val requestBodyJson = gson.toJson(requestBodyRaw)
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestBodyJson)

            val response = authApiService.login(requestBody)

            Resource.Success(loginResponseToModel(response))
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        })
    }

    override suspend fun register(email: String, password: String) {
        val gson = Gson()
        val requestBodyRaw = RegisterRequest(email, password)
        val requestBodyJson = gson.toJson(requestBodyRaw)
        val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())

       authApiService.register(requestBody)
    }

    override suspend fun logout() {
        authApiService.logout()
    }
}
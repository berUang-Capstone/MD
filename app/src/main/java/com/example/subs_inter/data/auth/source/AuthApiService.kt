package com.example.subs_inter.data.auth.source

import com.example.subs_inter.data.auth.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body loginBody: RequestBody): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body loginBody: RequestBody)

    @POST("auth/logout")
    suspend fun logout()
}
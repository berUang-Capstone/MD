package com.example.subs_inter.data.user.source

import com.example.subs_inter.data.user.response.WalletDataResponse
import com.example.subs_inter.data.user.response.WalletResponse
import retrofit2.http.GET

interface UserApiService {
    @GET("user/wallet")
    suspend fun fetchWallet(): WalletResponse
}
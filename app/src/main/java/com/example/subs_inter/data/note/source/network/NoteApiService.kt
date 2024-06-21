package com.example.subs_inter.data.note.source.network

import com.example.subs_inter.data.note.source.network.response.TrxHistoryResponse
import com.example.subs_inter.data.note.source.network.response.TrxResponse
import com.example.subs_inter.data.note.source.network.response.TrxScanResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NoteApiService {
    @GET("transaction")
    suspend fun fetchHistory(): TrxHistoryResponse

    @POST("transaction")
    suspend fun uploadTransaction(
        @Body
        transaction: RequestBody
    )

    @POST("transaction/type")
    suspend fun fetchTransactionByType(
        @Body
        body: RequestBody
    ): TrxResponse

    @POST("transaction/category")
    suspend fun fetchTransactionByCategory(
        @Body
        body: RequestBody
    ): TrxResponse

    @POST("transaction/date")
    suspend fun fetchTransactionByDate(
        @Body
        body: RequestBody
    ): TrxResponse

    @Multipart
    @POST("transaction/ocr")
    suspend fun scanTheReceipt(
        @Part file: MultipartBody.Part
    ): TrxScanResponse

}
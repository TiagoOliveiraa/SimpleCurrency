package com.toliveira.simplecurrency

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.Currency

interface ExchangeApiService {

    @GET("latest")
    suspend fun getLatestData(
        @Query("access_key") apiKey: String,
    ): Response<ExchangeDataResponse>

}
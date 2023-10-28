package com.toliveira.simplecurrency

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.Currency

interface ExchangeApiService {

    @GET("latest")
    @Headers("Authorization: a94e21810104cb8418f0bc957aab23e0")
    fun getLatestData(

        @Query("access_key") apiKey: String,
    ): Call<ExchangeDataResponse>

}
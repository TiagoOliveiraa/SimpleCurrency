package com.toliveira.simplecurrency

import android.provider.Settings.Global
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeRatesClient {

    private val BASE_URL = "http://api.exchangeratesapi.io/"
    private val apiKey = "a94e21810104cb8418f0bc957aab23e0"

    fun makeRetrofitService() : ExchangeApiService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ExchangeApiService::class.java)
    }


//    fun getExchangeRates(): Call<ExchangeDataResponse> {
//
//        return apiService.getLatestData(apiKey)
//    }


}
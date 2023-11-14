package com.toliveira.simplecurrency

import android.provider.Settings.Global
import com.toliveira.simplecurrency.GetApiKey.getApiKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeRatesClient {

    private val BASE_URL = "http://api.exchangeratesapi.io/"

    fun makeRetrofitService() : ExchangeApiService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ExchangeApiService::class.java)
    }

}
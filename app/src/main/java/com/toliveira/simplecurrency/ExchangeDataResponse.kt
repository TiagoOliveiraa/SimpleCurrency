package com.toliveira.simplecurrency

data class ExchangeDataResponse(
    val rates: Map<String, Double>,
    val base : String,
    val date : String
)

package com.toliveira.simplecurrency

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.toliveira.simplecurrency.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, ArrayList<String>())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        val exchangeClient = ExchangeRatesClient()

        val call = exchangeClient.getExchangeRates()
        println("This Call -> ${call.request().url()}")

        call.enqueue(object : Callback<ExchangeDataResponse> {
            override fun onResponse(
                call: Call<ExchangeDataResponse>,
                response: Response<ExchangeDataResponse>
            ) {
                if (response.isSuccessful) {
                    val exchange = response.body()

                    exchange?.rates?.forEach { (value, Key) ->
                        spinnerAdapter.add(value)
                    }

                    binding.currency.adapter = spinnerAdapter
                    binding.base.adapter = spinnerAdapter

                    binding.button.setOnClickListener {
                        exchangeResult(exchange)
                    }


                } else {
                    println("Erro!!!")
                    println(response.code())
                    println(response.message())
                }
            }

            override fun onFailure(call: Call<ExchangeDataResponse>, t: Throwable) {
                println("Erro na chamada!!")
                println(t.message)
            }
        })

    }

    private fun exchangeResult(exchange: ExchangeDataResponse?) {
        val initialValue = binding.value.text.toString().toDouble()
        var finalValue: Double = 0.0
        val spinnerChoice = binding.currency.selectedItem.toString()
        val baseChoice = binding.base.selectedItem.toString()
        var tmp : Double = 0.0

        if (exchange!!.rates.isNotEmpty()) {
            if (baseChoice == "EUR") {
                val exchangeValue = exchange.rates[spinnerChoice]
                finalValue = initialValue * exchangeValue!!.toDouble()
                binding.result.text = finalValue.toString()
            }
            else{
                tmp = initialValue / exchange.rates[baseChoice]!!.toDouble()
                finalValue = tmp * exchange.rates[spinnerChoice]!!.toDouble()
                binding.result.text = finalValue.toString()
            }
        }


    }
}
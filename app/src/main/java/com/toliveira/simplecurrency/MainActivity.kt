package com.toliveira.simplecurrency

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.toliveira.simplecurrency.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
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

        val service = ExchangeRatesClient().makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = GetApiKey.getApiKey(this@MainActivity)
            val response = service.getLatestData(apiKey)
            withContext(Dispatchers.Main) {
                try {
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
                        Toast.makeText(
                            this@MainActivity,
                            "Error ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Exception ${e.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Oooops: Something else went wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun exchangeResult(exchange: ExchangeDataResponse?) {
        val initialValue = binding.valueText.text.toString().toDouble()
        var finalValue: Double = 0.0
        val spinnerChoice = binding.currency.selectedItem.toString()
        val baseChoice = binding.base.selectedItem.toString()
        var tmp: Double = 0.0

        if (exchange!!.rates.isNotEmpty()) {
            if (baseChoice == "EUR") {
                val exchangeValue = exchange.rates[spinnerChoice]
                finalValue = initialValue * exchangeValue!!.toDouble()
                val presentedValue = String.format("%.3f $spinnerChoice",finalValue)
                binding.result.text = presentedValue
            } else {
                tmp = initialValue / exchange.rates[baseChoice]!!.toDouble()
                finalValue = tmp * exchange.rates[spinnerChoice]!!.toDouble()
                val presentedValue = String.format("%.3f $spinnerChoice",finalValue)
                binding.result.text = presentedValue
            }
        }


    }
}
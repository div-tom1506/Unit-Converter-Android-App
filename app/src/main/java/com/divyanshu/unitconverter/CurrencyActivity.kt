package com.divyanshu.unitconverter

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class CurrencyActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var tvResult: TextView
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var btnConvert: Button

    // has to be hidden
    private val API_KEY = BuildConfig.EXCHANGE_RATE_API_KEY
    private val BASE_URL = "https://v6.exchangerate-api.com/v6/$API_KEY/latest/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        etAmount = findViewById(R.id.etAmount)
        tvResult = findViewById(R.id.tvResult)
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency)
        btnConvert = findViewById(R.id.btnConvert)

        val currencyOptions = arrayOf(
            "USD", // US Dollar
            "EUR", // Euro
            "INR", // Indian Rupee
            "GBP", // British Pound
            "JPY", // Japanese Yen
            "CAD", // Canadian Dollar
            "AUD", // Australian Dollar
            "CNY", // Chinese Yuan
            "SGD", // Singapore Dollar
            "CHF", // Swiss Franc
            "NZD", // New Zealand Dollar
            "HKD", // Hong Kong Dollar
            "KRW", // South Korean Won
            "THB", // Thai Baht
            "BRL", // Brazilian Real
            "RUB", // Russian Ruble
            "ZAR", // South African Rand
            "AED", // UAE Dirham
            "SAR", // Saudi Riyal
            "MXN", // Mexican Peso
            "MYR", // Malaysian Ringgit
            "IDR", // Indonesian Rupiah
            "PHP", // Philippine Peso
            "TRY", // Turkish Lira
            "VND", // Vietnamese Dong
            "EGP", // Egyptian Pound
            "PLN", // Polish Zloty
            "NOK", // Norwegian Krone
            "SEK", // Swedish Krona
            "DKK", // Danish Krone
            "TWD", // Taiwan Dollar
            "ARS", // Argentine Peso
            "CLP", // Chilean Peso
            "COP", // Colombian Peso
            "CZK", // Czech Koruna
            "HUF", // Hungarian Forint
            "PKR"  // Pakistani Rupee
        )

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencyOptions)
        spinnerFromCurrency.adapter = adapter
        spinnerToCurrency.adapter = adapter

        btnConvert.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val fromCurrency = spinnerFromCurrency.selectedItem.toString()
        val toCurrency = spinnerToCurrency.selectedItem.toString()
        val amount = etAmount.text.toString().toDoubleOrNull()

        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "$BASE_URL$fromCurrency"
        // log 1
        Log.d("DebugTag1", "URL: $url")

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    handleApiError(response.code)
                    return
                }

                response.body?.let { responseBody ->
                    val jsonResponse = JSONObject(responseBody.string())
                    // log 2
                    Log.d("DebugTag2", "jsonResponse: $jsonResponse")
                    if (jsonResponse.has("conversion_rates")) {
                        val rate =
                            jsonResponse.getJSONObject("conversion_rates").getDouble(toCurrency)
                        // log 3
                        Log.d("DebugTag3", "rate: $rate")
                        val convertedAmount = amount * rate

                        runOnUiThread {
                            tvResult.text = "${String.format("%.2f", convertedAmount)}"
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Invalid response from server",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }

    private fun handleApiError(errorCode: Int) {
        runOnUiThread {
            val errorMessage = when (errorCode) {
                400 -> "Bad Request: Invalid input"
                404 -> "Not Found: API endpoint is incorrect"
                500 -> "Server Error: Try again later"
                else -> "Unexpected Error: Code $errorCode"
            }
            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

package com.divyanshu.unitconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TemperatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        // UI Elements
        val etTemperature = findViewById<EditText>(R.id.etTemperature)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val spinnerConversionType = findViewById<Spinner>(R.id.spinnerConversionType)

        // Spinner Items (Celsius ↔ Fahrenheit)
        val conversionOptions = arrayOf("Celsius to Fahrenheit", "Fahrenheit to Celsius")
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, conversionOptions)
        spinnerConversionType.adapter = adapter

        btnConvert.setOnClickListener {
            val inputTemp = etTemperature.text.toString()

            if (inputTemp.isNotEmpty()) {
                val tempValue = inputTemp.toDouble()
                val selectedConversion = spinnerConversionType.selectedItem.toString()

                val convertedTemp = when (selectedConversion) {
                    "Celsius to Fahrenheit" -> String.format("%.2f °F", (tempValue * 9 / 5) + 32)
                    "Fahrenheit to Celsius" -> String.format("%.2f °C", (tempValue - 32) * 5 / 9)
                    else -> "Invalid Selection"
                }

                tvResult.text = "Result: $convertedTemp"
            } else {
                Toast.makeText(this, "Please enter a temperature value!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

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

        val etTemperature = findViewById<EditText>(R.id.etTemperature)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val spinnerFromUnit = findViewById<Spinner>(R.id.spinnerFromUnit)
        val spinnerToUnit = findViewById<Spinner>(R.id.spinnerToUnit)

        val tempUnits = arrayOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tempUnits)
        spinnerFromUnit.adapter = adapter
        spinnerToUnit.adapter = adapter

        btnConvert.setOnClickListener {
            val inputTemp = etTemperature.text.toString()

            if (inputTemp.isNotEmpty()) {
                val tempValue = inputTemp.toDouble()
                val fromUnit = spinnerFromUnit.selectedItem.toString()
                val toUnit = spinnerToUnit.selectedItem.toString()

                val convertedTemp = convertTemperature(tempValue, fromUnit, toUnit)
                tvResult.text = "Result: ${String.format("%.2f", convertedTemp)} $toUnit"

            } else {
                Toast.makeText(this, "Please enter a temperature value!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {

        val celsius = when (fromUnit) {
            "Celsius (°C)" -> value
            "Fahrenheit (°F)" -> (value - 32) * 5/9
            "Kelvin (K)" -> value - 273.15
            else -> value
        }

        return when (toUnit) {
            "Celsius (°C)" -> celsius
            "Fahrenheit (°F)" -> (celsius * 9/5) + 32
            "Kelvin (K)" -> celsius + 273.15
            else -> celsius
        }
    }
}

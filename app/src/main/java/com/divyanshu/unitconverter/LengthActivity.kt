package com.divyanshu.unitconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LengthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_length)

        val etLength = findViewById<EditText>(R.id.etLength)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val spinnerFromUnit = findViewById<Spinner>(R.id.spinnerFromUnit)
        val spinnerToUnit = findViewById<Spinner>(R.id.spinnerToUnit)

        val lengthUnits = arrayOf(
            "Kilometers",
            "Meters",
            "Centimeters",
            "Millimeters",
            "Miles",
            "Yards",
            "Feet",
            "Inches"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lengthUnits)
        spinnerFromUnit.adapter = adapter
        spinnerToUnit.adapter = adapter

        btnConvert.setOnClickListener {
            val inputLength = etLength.text.toString()

            if (inputLength.isNotEmpty()) {
                val lengthValue = inputLength.toDouble()
                val fromUnit = spinnerFromUnit.selectedItem.toString()
                val toUnit = spinnerToUnit.selectedItem.toString()

                val convertedLength = convertLength(lengthValue, fromUnit, toUnit)
                tvResult.text = "Result: ${String.format("%.2f", convertedLength)} $toUnit"
            } else {
                Toast.makeText(this, "Please enter a length value!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        val baseMeters = when (fromUnit) {
            "Kilometers" -> value * 1000
            "Meters" -> value
            "Centimeters" -> value / 100
            "Millimeters" -> value / 1000
            "Miles" -> value * 1609.34
            "Yards" -> value * 0.9144
            "Feet" -> value * 0.3048
            "Inches" -> value * 0.0254
            else -> value
        }

        return when (toUnit) {
            "Kilometers" -> baseMeters / 1000
            "Meters" -> baseMeters
            "Centimeters" -> baseMeters * 100
            "Millimeters" -> baseMeters * 1000
            "Miles" -> baseMeters / 1609.34
            "Yards" -> baseMeters / 0.9144
            "Feet" -> baseMeters / 0.3048
            "Inches" -> baseMeters / 0.0254
            else -> baseMeters
        }

    }
}

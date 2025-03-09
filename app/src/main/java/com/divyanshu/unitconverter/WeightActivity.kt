package com.divyanshu.unitconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WeightActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        val etWeight = findViewById<EditText>(R.id.etWeight)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val spinnerFromUnit = findViewById<Spinner>(R.id.spinnerFromUnit)
        val spinnerToUnit = findViewById<Spinner>(R.id.spinnerToUnit)

        val weightUnits = arrayOf("Kilograms (kg)", "Grams (g)", "Pounds (lbs)", "Tons (t)")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, weightUnits)
        spinnerFromUnit.adapter = adapter
        spinnerToUnit.adapter = adapter

        btnConvert.setOnClickListener {
            val weight = etWeight.text.toString()

            if (weight.isNotEmpty()) {
                val weightValue = weight.toDouble()
                val fromUnit = spinnerFromUnit.selectedItem.toString()
                val toUnit = spinnerToUnit.selectedItem.toString()

                val convertedWeight = convertWeight(weightValue, fromUnit, toUnit)
                tvResult.text = "Result: ${String.format("%.2f", convertedWeight)} $toUnit"

            } else {
                Toast.makeText(this, "Please enter weight value!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {

        val kg = when (fromUnit) {
            "Kilograms (kg)" -> value
            "Grams (g)" -> value / 1000
            "Pounds (lbs)" -> value * 0.453592
            "Tons (t)" -> value * 1000
            else -> value
        }

        return when (toUnit) {
            "Kilograms (kg)" -> kg
            "Grams (g)" -> kg * 1000
            "Pounds (lbs)" -> kg / 0.453592
            "Tons (t)" -> kg / 1000
            else -> kg
        }
    }

}

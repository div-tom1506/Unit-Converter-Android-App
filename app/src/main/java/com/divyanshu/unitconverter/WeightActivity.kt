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
        val spinnerConversionType = findViewById<Spinner>(R.id.spinnerConversionType)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val conversionOptions = arrayOf(
            "Kg to Grams",
            "Grams to Kg",
            "Kg to Pounds",
            "Pounds to Kg",
            "Grams to Pounds",
            "Pounds to Grams",
            "Kg to Tons",
            "Tons to Kg",
            "Pounds to Ounces",
            "Ounces to Pounds"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, conversionOptions)
        spinnerConversionType.adapter = adapter

        btnConvert.setOnClickListener {
            val weight = etWeight.text.toString()

            if (weight.isNotEmpty()) {
                val weightValue = weight.toDouble()
                val spinnerConversationSelection = spinnerConversionType.selectedItem.toString()

                val convertedWeight = when (spinnerConversationSelection) {
                    "Kg to Grams" -> weightValue * 1000
                    "Grams to Kg" -> weightValue / 1000
                    "Kg to Pounds" -> weightValue * 2.2046
                    "Pounds to Kg" -> weightValue / 2.2046
                    "Grams to Pounds" -> weightValue / 453.592
                    "Pounds to Grams" -> weightValue * 453.592
                    "Kg to Tons" -> weightValue / 1000
                    "Tons to Kg" -> weightValue * 1000
                    "Pounds to Ounces" -> weightValue * 16
                    "Ounces to Pounds" -> weightValue / 16
                    else -> 0.0
                }

                tvResult.text = "Result: ${String.format("%.2f", convertedWeight)} ${getUnit(spinnerConversationSelection)}"
                    
            } else {
                Toast.makeText(this, "Please enter weight value!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUnit(spinnerConversationSelection: String) : String {
        return when (spinnerConversationSelection) {
            "Kg to Grams" -> "g"
            "Grams to Kg" -> "Kg"
            "Kg to Pounds" -> "lbs"
            "Pounds to Kg" -> "Kg"
            "Grams to Pounds" -> "lbs"
            "Pounds to Grams" -> "g"
            "Kg to Tons" -> "tons"
            "Tons to Kg" -> "kg"
            "Pounds to Ounces" -> "ozs"
            "Ounces to Pounds" -> "lbs"
            else -> ""
        }
    }
}

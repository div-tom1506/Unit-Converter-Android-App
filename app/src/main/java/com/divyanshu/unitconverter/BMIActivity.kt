package com.divyanshu.unitconverter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class BMIActivity : AppCompatActivity() {

    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var buttonCalculateBMI: Button
    private lateinit var textViewBMIResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        // Initialize UI Elements
        editTextWeight = findViewById(R.id.editTextWeight)
        editTextHeight = findViewById(R.id.editTextHeight)
        buttonCalculateBMI = findViewById(R.id.buttonCalculateBMI)
        textViewBMIResult = findViewById(R.id.textViewBMIResult)

        // Button Click Listener
        buttonCalculateBMI.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val weightString = editTextWeight.text.toString()
        val heightString = editTextHeight.text.toString()

        if (weightString.isNotEmpty() && heightString.isNotEmpty()) {
            try {
                val weight = weightString.toFloat()
                val height = heightString.toFloat() / 100 // Convert cm to meters

                if (height > 0) {
                    val bmi: Double =
                        weight.toDouble() / (height.toDouble() * height.toDouble()) // Calculate as Double
                    val decimalFormat = DecimalFormat("#.##")
                    val formattedBMI = decimalFormat.format(bmi)

                    textViewBMIResult.text = "BMI: $formattedBMI\n${getBMICategory(bmi)}"
                } else {
                    textViewBMIResult.text = "Invalid height."
                }
            } catch (e: NumberFormatException) {
                textViewBMIResult.text = "Invalid input. Please enter valid numbers."
            }
        } else {
            textViewBMIResult.text = "Please enter weight and height."
        }
    }

    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal weight"
            bmi < 30 -> "Overweight"
            else -> "Obese"
        }
    }
}

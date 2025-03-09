package com.divyanshu.unitconverter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class BMIActivity : AppCompatActivity() {

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnCalculateBMI: Button
    private lateinit var tvBMIResult: TextView
    private lateinit var tvBMICategory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        btnCalculateBMI = findViewById(R.id.btnCalculateBMI)
        tvBMIResult = findViewById(R.id.tvBMIResult)
        tvBMICategory = findViewById(R.id.tvBMICategory)

        btnCalculateBMI.setOnClickListener {
            val weightInput = etWeight.text.toString()
            val heightInput = etHeight.text.toString()

            if (weightInput.isNotEmpty() && heightInput.isNotEmpty()) {
                val weight = weightInput.toDouble()
                val height = heightInput.toDouble() / 100

                val bmi = calculateBMI(weight, height)
                val category = getBMICategory(bmi)

                tvBMIResult.text = "BMI: ${String.format("%.2f", bmi)}"
                tvBMICategory.text = "Category: $category"
            } else {
                Toast.makeText(this, "Please enter both weight and height!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun calculateBMI(weight: Double, height: Double): Double {
        return weight / (height * height)
    }

    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal"
            bmi in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }
}

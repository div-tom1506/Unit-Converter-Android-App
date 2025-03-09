package com.divyanshu.unitconverter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnTemperature).setOnClickListener {
            startActivity(Intent(this, TemperatureActivity::class.java))
        }

        findViewById<Button>(R.id.btnWeight).setOnClickListener {
            startActivity(Intent(this, WeightActivity::class.java))
        }

        findViewById<Button>(R.id.btnCurrency).setOnClickListener {
            startActivity(Intent(this, CurrencyActivity::class.java))
        }

        findViewById<Button>(R.id.btnBMI).setOnClickListener {
            startActivity(Intent(this, BMIActivity::class.java))
        }

        findViewById<Button>(R.id.btnCalculator).setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        findViewById<Button>(R.id.btnConvertLength).setOnClickListener {
            startActivity(Intent(this, LengthActivity::class.java))
        }

        findViewById<Button>(R.id.btnWorldTime).setOnClickListener {
            startActivity(Intent(this, WorldTimeActivity::class.java))
        }
    }
}
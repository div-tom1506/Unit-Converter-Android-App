package com.divyanshu.unitconverter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput: String = ""
    private var lastOperator: Char? = null
    private var lastNumber: String = ""
    private var isNewInput: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        tvResult = findViewById(R.id.tvResult)

        val buttonIds = listOf(
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn0, R.id.btnDot
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                appendToInput((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { applyOperator('+') }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { applyOperator('-') }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { applyOperator('*') }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { applyOperator('/') }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { applyPercentage() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.btnBrackets).setOnClickListener { applyBrackets() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { toggleSign() }

    }

    private fun appendToInput(value: String) {
        if (isNewInput) {
            tvResult.text = ""
            isNewInput = false
        }
        currentInput += value
        tvResult.text = currentInput

    }

    private fun applyOperator(operator: Char) {
        if (currentInput.isNotEmpty() && lastOperator == null) {
            lastOperator = operator
            lastNumber = currentInput
            currentInput += " $operator "
            tvResult.text = currentInput
        }
    }

    private fun applyPercentage() {
        if (currentInput.isNotEmpty()) {
            val result = currentInput.toDouble() / 100
            currentInput = result.toString()
            tvResult.text = currentInput
        }
    }

    private fun clearInput() {
        currentInput = ""
        lastOperator = null
        lastNumber = ""
        isNewInput = true
        tvResult.text = "0"
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            tvResult.text = currentInput
        }
    }

    private fun applyBrackets() {
        currentInput += if (currentInput.isEmpty() || currentInput.last() == ' ') {
            "("
        } else {
            ")"
        }
        tvResult.text = currentInput
    }

    private fun calculateResult() {
        try {
            val expression = currentInput.replace("×", "*").replace("÷", "/")
            val result = evaluateExpression(expression)
            currentInput = result.toString()
            tvResult.text = result.toString()
            isNewInput = true
        } catch (e: Exception) {
            tvResult.text = "Error"
            currentInput = ""
            isNewInput = true
        }
    }

    private fun evaluateExpression(expression: String): Double {
        return try {
            ExpressionBuilder(expression.replace("×", "*").replace("÷", "/")).build().evaluate()
        } catch (e: Exception) {
            0.0
        }
    }

}

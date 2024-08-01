package com.trios2024evmg.calculator

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var number1Text: EditText
    private lateinit var number2Text: EditText
    private lateinit var signText: TextView
    private lateinit var resultText: TextView
    private lateinit var calculateButton: Button
    private lateinit var plusButton: Button
    private lateinit var minusButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var divideButton: Button
    private lateinit var previousOperation: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number1Text = findViewById(R.id.firstNumber)
        number2Text = findViewById(R.id.secondNumber)
        signText = findViewById(R.id.sign)
        resultText = findViewById(R.id.result)
        calculateButton = findViewById(R.id.calculate)
        plusButton = findViewById(R.id.plus)
        minusButton = findViewById(R.id.minus)
        multiplyButton = findViewById(R.id.multiply)
        divideButton = findViewById(R.id.divide)
        previousOperation = findViewById(R.id.previousOperation)

        plusButton.setOnClickListener {
            signText.text = getString(R.string.plus)
        }
        minusButton.setOnClickListener {
            signText.text = getString(R.string.minus)
        }
        multiplyButton.setOnClickListener {
            signText.text = getString(R.string.multiply)
        }
        divideButton.setOnClickListener {
            signText.text = getString(R.string.divide)
        }

        calculateButton.setOnClickListener { view ->
            val bounceAnime = AnimationUtils.loadAnimation(this, R.anim.button)
            view.startAnimation(bounceAnime)
            if (calculateButton.text == getString(R.string.calculate)) {
                calculateResult()
                hideKeyboardAndDeactivateFields()
                calculateButton.text = getString(R.string.reset)
                calculateButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
            } else {
                resetFields()
            }
        }
    }


    private fun calculateResult() {
        val firstNumber = number1Text.text.toString()
        val secondNumber = number2Text.text.toString()
        val sign = signText.text.toString()

        if (firstNumber.isEmpty() || secondNumber.isEmpty() || sign.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers and select an operation", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val firstNum = firstNumber.toDouble()
            val secondNum = secondNumber.toDouble()

            val result = if (sign == "+") {
                firstNum + secondNum
            } else if (sign == "-") {
                firstNum - secondNum
            } else if (sign == "*") {
                firstNum * secondNum
            } else if (sign == "/") {
                if (secondNum != 0.0) {
                    firstNum / secondNum
                } else {
                    Toast.makeText(this, "Error: Divide by zero", Toast.LENGTH_SHORT).show()
                    return
                }
            } else {
                Toast.makeText(this, "Invalid operation", Toast.LENGTH_SHORT).show()
                return
            }

            resultText.text = String.format("%.2f", result)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
        }
    }


    private fun hideKeyboardAndDeactivateFields() {
        // Hide the keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        // Deactivate the EditText fields
        number1Text.isEnabled = false
        number2Text.isEnabled = false
    }


    private fun resetFields() {
        // Reactivate the EditText fields
        number1Text.isEnabled = true
        number2Text.isEnabled = true
        val firstNumber = number1Text.text.toString()
        val secondNumber = number2Text.text.toString()
        val result = resultText.text.toString()
        val sign = signText.text.toString()
        // Clear the EditText fields and TextViews
        number1Text.text.clear()
        number2Text.text.clear()
        signText.text = ""
        resultText.text = ""

        // Reset the button text
        calculateButton.text = getString(R.string.calculate)
        calculateButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        previousOperation.text = getString(R.string.previous_operation, firstNumber, sign, secondNumber, result)

    }

}


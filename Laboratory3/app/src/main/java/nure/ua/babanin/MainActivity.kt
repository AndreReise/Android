package nure.ua.babanin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var inputEditText: TextView
    private lateinit var calculatorButtonsLayout: GridLayout


    private var currentInput: StringBuilder = StringBuilder()
    private var currentOperator: String? = null
    private var operand1: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.txtInput)
        calculatorButtonsLayout = findViewById(R.id.calculatorButtonsLayout)

        val digitButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9)

        digitButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { onDigitClick(it) }
        }

        val operatorButtons = listOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide)

        operatorButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { onOperatorClick(it) }
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { onDecimalClick() }
    }

    private fun onDigitClick(view: View) {
        val digit = (view as Button).text.toString()
        currentInput.append(digit)
        updateInputDisplay()
    }

    private fun onOperatorClick(view: View) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toString().toDouble()
            currentInput.clear()
            currentOperator = (view as Button).text.toString()
        }
    }

    private fun onEqualsClick() {
        if (currentOperator != null && currentInput.isNotEmpty()) {
            val operand2 = currentInput.toString().toDouble()
            val result = when (currentOperator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> 0.0
            }
            currentInput.clear()
            currentInput.append(result)
            updateInputDisplay()
            currentOperator = null
        }
    }

    private fun onClearClick() {
        currentInput.clear()
        operand1 = 0.0
        currentOperator = null
        updateInputDisplay()
    }

    private fun onDecimalClick() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput.append("0")
            }
            currentInput.append(".")
            updateInputDisplay()
        }
    }

    private fun updateInputDisplay() {
        inputEditText.setText(currentInput.toString())
    }
}
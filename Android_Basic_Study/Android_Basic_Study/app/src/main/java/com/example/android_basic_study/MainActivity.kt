package com.example.android_basic_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.android_basic_study.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.btnNum0 -> numberButtonClicked("0")
            R.id.btnNum1 -> numberButtonClicked("1")
            R.id.btnNum2 -> numberButtonClicked("2")
            R.id.btnNum3 -> numberButtonClicked("3")
            R.id.btnNum4 -> numberButtonClicked("4")
            R.id.btnNum5 -> numberButtonClicked("5")
            R.id.btnNum6 -> numberButtonClicked("6")
            R.id.btnNum7 -> numberButtonClicked("7")
            R.id.btnNum8 -> numberButtonClicked("8")
            R.id.btnNum9 -> numberButtonClicked("9")

            R.id.btnDiv -> operatorButtonClicked("/")
            R.id.btnRest -> operatorButtonClicked("%")
            R.id.btnNumPlus -> operatorButtonClicked("+")
            R.id.btnSubt -> operatorButtonClicked("-")
            R.id.btnMult -> operatorButtonClicked("X")
        }
    }

    // 숫자와 연산자 버튼 인식
    private fun numberButtonClicked(num: String) {
        if (isOperator){
            binding.textView.append(" ")
        }
        isOperator =false

        binding.textView.append(num)
        // resultTextView실시간으로 계산결과를 넣음
        binding.textView2.text = calculateExpression()
    }
    private fun operatorButtonClicked(operator: String) {
        if (binding.textView.text.isEmpty()) {
            return
        }
        // 연산자가 있을 경우
        when {
            isOperator -> {
                val text = binding.textView.text.toString()
                binding.textView.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                binding.textView.append(" $operator")
            }
        }
        isOperator = true
        hasOperator = true
    }
    // 결과값 내기
    fun resultButtonClicked(v: View) {
        val expressionTexts = binding.textView.text.split("")
        if (binding.textView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        binding.textView.text = binding.textView2.text
        binding.textView2.text = ""

        isOperator = false
        hasOperator = false
    }
    private fun calculateExpression(): String {
        val expressionTexts = binding.textView.text.split(" ")
        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "X" -> (exp1 * exp2).toString()
            "%" -> (exp1 % exp2).toString()
            "/" -> (exp1 / exp2).toString()
            else -> ""
        }
    }
    fun clearButtonClicked(v: View) {
        binding.textView.text = ""
        binding.textView2.text = ""
        isOperator = false
        hasOperator =false
    }
    fun String.isNumber(): Boolean {
        return try {
            this.toBigInteger()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
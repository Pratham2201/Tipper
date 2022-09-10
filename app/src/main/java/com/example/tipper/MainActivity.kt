package com.example.tipper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipper.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener {
            calculateTip()
        }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTip() {

        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        if(cost == null) {
            binding.resultTip.text = ""
            return
        }


        val tipPercent = when(binding.tipOptions.checkedRadioButtonId) {
            R.id.tipAmazing -> 0.20
            R.id.tipGood -> 0.18
            else-> 0.15
        }
        var tipAmt = cost * tipPercent

        if(binding.roundUpSwitch.isChecked)
        {
            tipAmt = ceil(tipAmt)
        }
        val formattedTip = NumberFormat.getInstance().format(tipAmt)
        binding.resultTip.text = getString(R.string.tipAmt, formattedTip)
    }
}
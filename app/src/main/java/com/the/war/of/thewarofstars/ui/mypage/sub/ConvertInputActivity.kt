package com.the.war.of.thewarofstars.ui.mypage.sub

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityConvertInputBinding
import java.text.DecimalFormat

class ConvertInputActivity: BaseActivity<ActivityConvertInputBinding>(R.layout.activity_convert_input) {

    private val TAG = "ConvertInputActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            etInput.apply {
                setOnDeleteButton()
            }

            tvNumberOne.apply {
                setOnClickListener {
                    clickNumber(ONE)
                }
            }

            tvNumberTwo.apply {
                setOnClickListener {
                    clickNumber(TWO)
                }
            }

            tvNumberThree.apply {
                setOnClickListener {
                    clickNumber(THREE)
                }
            }

            tvNumberFour.apply {
                setOnClickListener {
                    clickNumber(FOUR)
                }
            }

            tvNumberFive.apply {
                setOnClickListener {
                    clickNumber(FIVE)
                }
            }

            tvNumberSix.apply {
                setOnClickListener {
                    clickNumber(SIX)
                }
            }

            tvNumberSeven.apply {
                setOnClickListener {
                    clickNumber(SEVEN)
                }
            }

            tvNumberEight.apply {
                setOnClickListener {
                    clickNumber(EIGHT)
                }
            }

            tvNumberNine.apply {
                setOnClickListener {
                    clickNumber(NINE)
                }
            }

            tvNumberZero.apply {
                setOnClickListener {
                    clickNumber(ZERO)
                }
            }

            tvNumberDoubleZero.apply {
                setOnClickListener {
                    clickNumber(DOUBLE_ZERO)
                }
            }

            tvNumberErase.apply {
                setOnClickListener {
                    clickNumber(ERAISE)
                }
            }

            tvAutoAccountAll.apply {
                setOnClickListener {
                    clickNumber(ALL)
                }
            }

            tvAutoAccount100.apply {
                setOnClickListener {
                    clickNumber(MILLION)
                }
            }

            tvAutoAccount10.apply {
                setOnClickListener {
                    clickNumber(ONE_HUNDRED_THOUSAND)
                }
            }

            tvAutoAccount5.apply {
                setOnClickListener {
                    clickNumber(FIFTEEN_THOUSAND)
                }
            }

            tvAutoAccount1.apply {
                setOnClickListener {
                    clickNumber(TEN_THOUSAND)
                }
            }


        }

    }

    private fun clickNumber(number: String) {
        if (dataBinding.etInput.text.toString().length >= MAX_LENGTH && number != ERAISE)
            return

        when (number) {
            ERAISE -> {
                if (dataBinding.etInput.text.toString().length <= 1) {
                    dataBinding.etInput.text = ""
                    return
                }

                val formatter        = DecimalFormat("###,###")
                val input            = dataBinding.etInput.text.toString()
                val inputEraiseComma = (input).replace(",", "")
                val inputInt         = inputEraiseComma.substring(0, inputEraiseComma.length - 1).toInt()
                val result           = formatter.format(inputInt)

                dataBinding.etInput.text = result
            }
            MILLION -> {
                if (isConvertPointLessThan(MILLION))
                    input(number)

            }
            ONE_HUNDRED_THOUSAND -> {
                if (isConvertPointLessThan(ONE_HUNDRED_THOUSAND))
                    input(number)
            }
            FIFTEEN_THOUSAND -> {
                if (isConvertPointLessThan(FIFTEEN_THOUSAND))
                    input(number)
            }
            TEN_THOUSAND -> {
                if (isConvertPointLessThan(TEN_THOUSAND))
                    input(number)
            }
            else -> {
                input(number)
            }
        }
    }

    private fun isConvertPointLessThan(newInputPoint: String): Boolean {
        val newInputPoint = newInputPoint.toInt()
        val originPoint = if (dataBinding.etInput.text.toString().isNullOrEmpty())
                                0
                            else
                                dataBinding.etInput.text.toString().replace(",", "").toInt()
        val resulrPoint = (originPoint + newInputPoint)
        if (resulrPoint > newInputPoint) {
            if (originPoint < newInputPoint) {
                dataBinding.etInput.text = ""
                return true
            }
            return false
        }
        return true
    }

    private fun input(number: String) {
        val formatter        = DecimalFormat("###,###")
        val input            = dataBinding.etInput.text.toString()
        val inputEraiseComma = (input + number).replace(",", "")
        val inputInt         = inputEraiseComma.toInt()
        var result           = formatter.format(inputInt)

        if (result.length >= MAX_LENGTH) {
            val resultEraiseComma = result.replace(",", "")
            val resultString      = resultEraiseComma.substring(0, 8).toInt()
            result = formatter.format(resultString)
        }

        dataBinding.etInput.text = result
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun TextView.setOnDeleteButton() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 0) {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    val img = R.mipmap.icon_close_white
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, img, 0)
                }

                checkConvertButtonEnable()
            }
        })
    }

    private fun checkConvertButtonEnable() {
        // 1. 금액이 입력되지 않았을시 비활성화
        // 2. 금액이 한도액을 넘었을시 비활성화
//        val email = dataBinding.etvEmail.text.toString()
//        val password = dataBinding.etvPassword.text.toString()
//
//        dataBinding.btnLogin.isSelected = email.isNotEmpty()
//                && password.isNotEmpty()
//                && password.length >= 8
    }

    companion object {
        const val ERAISE      = "-1"
        const val ONE         = "1"
        const val TWO         = "2"
        const val THREE       = "3"
        const val FOUR        = "4"
        const val FIVE        = "5"
        const val SIX         = "6"
        const val SEVEN       = "7"
        const val EIGHT       = "8"
        const val NINE        = "9"
        const val ZERO        = "0"
        const val DOUBLE_ZERO = "00"

        const val ALL = "500000"
        const val MILLION = "1000000"
        const val ONE_HUNDRED_THOUSAND = "100000"
        const val FIFTEEN_THOUSAND = "50000"
        const val TEN_THOUSAND = "10000"

        const val MAX_LENGTH = 10
    }
}
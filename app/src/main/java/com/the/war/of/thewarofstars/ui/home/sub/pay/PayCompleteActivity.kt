package com.the.war.of.thewarofstars.ui.home.sub.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityPayCompleteBinding
import java.text.DecimalFormat

class PayCompleteActivity: BaseActivity<ActivityPayCompleteBinding>(R.layout.activity_pay_complete) {

    private var isPaySelected: Boolean = true

    private var price: Long? = null
    private var request: String? = null
    private var payDate: String? = null

    private val TAG = "PayCompleteActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeValues()

        initializeUnderLine()

        binding {

            tvPayOk.apply {
                setOnClickListener {
                    isSelected = true
                    tvPayCancel.isSelected = false
                    isPaySelected = true
                }
            }

            tvPayCancel.apply {
                setOnClickListener {
                    isSelected = false
                    tvPayOk.isSelected = false
                    isPaySelected = false
                }
            }
        }

    }

    private fun initializeUnderLine() {
        val content = SpannableString(dataBinding.tvPayOkGuide.text.toString())
        content.setSpan(UnderlineSpan(), 18, 35, 0)
        dataBinding.tvPayOkGuide.text = content
    }

    private fun initializeValues() {
        payDate = intent.getStringExtra("payDate")
        request = intent.getStringExtra("request")
        price   = intent.getLongExtra("price", 0)

        Log.i(TAG, "initializeValues\n " +
                "payDate : $payDate\n " +
                "request : $request\n " +
                "price : $price")

        dataBinding.tvRequestBeforeGameContent.text = request
        dataBinding.tvPrice.text                    = DecimalFormat("###,###").format(price) + "원"
        dataBinding.tvPayDate.text                  = payDate
    }
}
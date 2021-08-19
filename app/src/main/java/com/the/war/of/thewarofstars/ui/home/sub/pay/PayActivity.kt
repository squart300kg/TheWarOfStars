package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.os.Bundle
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PG
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityPayBinding


class PayActivity: BaseActivity<ActivityPayBinding>(R.layout.activity_pay){

    private val TAG = "PayActivityLog"

    private var isPersonalUsageTermsClicked = false
    private var isPayServiceTermsClicked    = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)

        // 피드백받을 이메일에 밑줄
        initializeUnderLine()

        // 초기 뷰 세팅
        initializeView()

        binding {

            tvPayPhone.apply {
                setOnClickListener {
                    tvPayPhone.isSelected = true
                    tvPayKakao.isSelected = false
                    tvPaySamsung.isSelected = false
                }
            }

            tvPayKakao.apply {
                setOnClickListener {
                    tvPayPhone.isSelected = false
                    tvPayKakao.isSelected = true
                    tvPaySamsung.isSelected = false
                }
            }

            tvPaySamsung.apply {
                setOnClickListener {
                    tvPayPhone.isSelected = false
                    tvPayKakao.isSelected = false
                    tvPaySamsung.isSelected = true
                }
            }

            layoutTermsPersonalUsageTitle.apply {
                setOnClickListener {
                    when (isPersonalUsageTermsClicked) {
                        true -> {
                            tvTermsPersonalUsageContent.visibility = View.GONE
                            isPersonalUsageTermsClicked = false
                        }
                        false -> {
                            tvTermsPersonalUsageContent.visibility = View.VISIBLE
                            tvTermsPersonalUsageContent.movementMethod = ScrollingMovementMethod()
                            isPersonalUsageTermsClicked = true
                        }
                    }

                }
            }
            layoutTermsPayServiceTitle.apply {
                setOnClickListener {
                    when(isPayServiceTermsClicked) {
                        true -> {
                            tvTermsPayServiceContent.visibility = View.GONE
                            isPayServiceTermsClicked = false
                        }
                        false -> {
                            tvTermsPayServiceContent.visibility = View.VISIBLE
                            tvTermsPayServiceContent.movementMethod = ScrollingMovementMethod()
                            isPayServiceTermsClicked = true
                        }
                    }
                }
            }
        }





    }

    private fun initializeView() {
        dataBinding.tvPayPhone.isSelected = true
    }

    private fun initializeUnderLine() {
        val content = SpannableString(dataBinding.tvGuideFeedbackEmail.text.toString())
        content.setSpan(UnderlineSpan(), 7, 24, 0)
        dataBinding.tvGuideFeedbackEmail.text = content
    }

    fun pay() {
        val request = IamPortRequest(
            pg = PG.nice.makePgRawName(),              // PG 사
            pay_method = PayMethod.phone,                     // 결제수단
            name = "테스트결제name",                         // 주문명
            merchant_uid = "테스트결제uid",                 // 주문번호
            amount = "1",                            // 결제금액
            buyer_name = "남궁안녕"
        )

        // 결제요청
        Iamport.payment("iamport",
            iamPortRequest = request,
            approveCallback = {
                /* 최종 결제전 콜백 함수. */
                Log.i(TAG, "approveCallback")
            },
            paymentResultCallback = {
                /* 최종 결제결과 콜백 함수. */
                Log.i(TAG, "approveCallback")
            })

    }

}
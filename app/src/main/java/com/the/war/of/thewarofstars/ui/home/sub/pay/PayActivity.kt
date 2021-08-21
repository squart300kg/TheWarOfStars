package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.data.sdk.PG
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.ICallbackPaymentResult
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.Event
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.PayType
import com.the.war.of.thewarofstars.databinding.ActivityPayBinding
import com.the.war.of.thewarofstars.util.DateUtil
import org.json.JSONObject
import java.text.DecimalFormat


class PayActivity: BaseActivity<ActivityPayBinding>(R.layout.activity_pay){

    private val TAG = "PayActivityLog"

    private var isPersonalUsageTermsClicked = false
    private var isPayServiceTermsClicked    = false

    private var payType: PayType = PayType.PHONE
    private var productName: String? = null
    private var sellerName: String? = null
    private var buyerName: String? = null
    private var price: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)

        // 피드백받을 이메일에 밑줄
        initializeUnderLine()

        initializeValues()

        // 초기 뷰 세팅
        initializeView()


        binding {

            tvPayPhone.apply {
                setOnClickListener {
                    tvPayPhone.isSelected = true
                    tvPayKakao.isSelected = false
                    tvPaySamsung.isSelected = false
                    payType = PayType.PHONE
                }
            }

            tvPayKakao.apply {
                setOnClickListener {
                    tvPayPhone.isSelected = false
                    tvPayKakao.isSelected = true
                    tvPaySamsung.isSelected = false
                    payType = PayType.KAKAO
                }
            }

            tvPaySamsung.apply {
                setOnClickListener {
                    tvPayPhone.isSelected = false
                    tvPayKakao.isSelected = false
                    tvPaySamsung.isSelected = true
                    payType = PayType.SAMSUNG
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

            tvPay.apply {
                setOnClickListener {
                var request: IamPortRequest? = null

                val merchant_uid = "S:$sellerName|B:$buyerName|T:${DateUtil.getCurrentDateString()}"
                    Log.i(TAG, "clickPay\nmerchant_uid : $merchant_uid")

                    when (payType) {
                        PayType.PHONE -> {
                            request = IamPortRequest(
                                pg           = PG.danal.makePgRawName(pgId = ""),
                                pay_method   = PayMethod.phone,
                                name         = productName,
                                merchant_uid = merchant_uid,
                                amount       = requireNotNull(price.toString()),
                                buyer_name   = buyerName
                            )
                        }
                        PayType.KAKAO -> {
                            request = IamPortRequest(
                                pg           = PG.nice.makePgRawName(pgId = ""),
                                pay_method   = PayMethod.kakaopay,
                                name         = productName,
                                merchant_uid = merchant_uid,
//                                amount       = requireNotNull(price.toString()),
                                amount       = "100",
                                buyer_name   = buyerName
                            )
                        }
                        PayType.SAMSUNG -> {
                            request = IamPortRequest(
                                pg           = PG.nice.makePgRawName(pgId = ""),
                                pay_method   = PayMethod.samsung,
                                name         = productName,
                                merchant_uid = merchant_uid,
//                                amount       = requireNotNull(price.toString()),
                                amount       = "100",
                                buyer_name   = buyerName
                            )
                        }
                    }

                    // 결제요청
                    Iamport.payment(getString(R.string.import_franchisee_code),
                        iamPortRequest = request!!,
                        approveCallback = {
                            /* 최종 결제전 콜백 함수. */
                            Log.i(TAG, "결제전")
                        },
                        paymentResultCallback = {
                            /* 최종 결제결과 콜백 함수. */
                            callBackListener.result(it)

                        })
                }
            }
        }

    }

    private val callBackListener = object : ICallbackPaymentResult {
        override fun result(iamPortResponse: IamPortResponse?) {
            val resJson = GsonBuilder().setPrettyPrinting().create().toJson(iamPortResponse)

            Log.i(TAG, "callBackListener1\n $resJson")

            if (iamPortResponse != null) {

                Log.i(TAG, "callBackListener2\n $resJson")

                val jsonObject = JSONObject(resJson)
                val isSuccess    = jsonObject.getString("imp_success").toBoolean()
                val merchant_uid = jsonObject.getString("merchant_uid")

                Log.i(TAG, "callBackListener3\n " +
                        "isSuccess : $isSuccess\n " +
                        "merchant_uid : $merchant_uid")

                if (isSuccess) {
                    goToPayCompleteActivity()
                } else {
                    val errorMessage = jsonObject.getString("error_msg")
                    Toast.makeText(this@PayActivity, errorMessage, Toast.LENGTH_LONG).show()
                }


            }
        }
    }
    private fun goToPayCompleteActivity() {
        Intent(this@PayActivity, PayCompleteActivity::class.java).apply {
            putExtra("request", dataBinding.etRequestBeforeGame.text.toString())
            putExtra("price", price)
            putExtra("payDate", DateUtil.getCurrentDateForPayComplete())
            finish()
            startActivity(this)
        }

    }

    private fun initializeValues() {
        productName = intent.getStringExtra("productName")
        sellerName  = intent.getStringExtra("sellerName")
        buyerName   = intent.getStringExtra("buyerName")
        price       = intent.getLongExtra("price", 0)
    }

    private fun initializeView() {
        dataBinding.tvPayPhone.isSelected = true
        dataBinding.etEmail.setText(Application.instance?.userEmail)
        dataBinding.tvPay.text = DecimalFormat("###,###").format(price) + "원 결제 진행하기"
    }

    private fun initializeUnderLine() {
        val content = SpannableString(dataBinding.tvGuideFeedbackEmail.text.toString())
        content.setSpan(UnderlineSpan(), 7, 24, 0)
        dataBinding.tvGuideFeedbackEmail.text = content
    }


}
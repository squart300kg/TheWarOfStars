package com.the.war.of.thewarofstars.ui.home.sub.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PG
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityPayBinding

class PayActivity: BaseActivity<ActivityPayBinding>(R.layout.activity_pay){

    private val TAG = "PayActivityLog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)


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
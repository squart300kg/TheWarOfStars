package com.the.war.of.thewarofstars.util

import android.content.Context
import android.util.Log
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PG
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.the.war.of.thewarofstars.R

class PayModule {

    private lateinit var sellerName : String
    private lateinit var buyerName : String
    private lateinit var productName : String
    private lateinit var context : Context

    private val TAG = "PayModuleLog"

    constructor(sellerName : String, buyerName : String, productName : String, context: Context) {
        this.sellerName = sellerName
        this.buyerName = buyerName
        this.productName = productName
        this.context = context
    }

    fun requestPayUsing(type : String) {

        val payItem = returnPayItem(type)

        requestPayWith(payItem)

    }

    private fun requestPayWith(payItem: IamPortRequest) {
        Iamport.payment(context.getString(R.string.import_franchisee_code),
            iamPortRequest = payItem,
            approveCallback = {
                /* 최종 결제전 콜백 함수. */
                Log.i(TAG, "결제전")
            },
            paymentResultCallback = {
                /* 최종 결제결과 콜백 함수. */
//                callBackListener.result(it)

            })
    }



    private fun returnPayItem(type: String): IamPortRequest {

        val payMerchantUid = returnMerchantUid()

        return when (type) {
            PHONE -> {
                IamPortRequest(
                    pg           = PG.danal.makePgRawName(pgId = ""),
                    pay_method   = PayMethod.phone,
                    name         = productName,
                    merchant_uid = payMerchantUid,
//                    amount       = requireNotNull(price.toString()),
                    amount       = "100",
                    buyer_name   = buyerName,
                    digital      = true
                )
            }
            KAKAO -> {
                IamPortRequest(
                    pg           = PG.nice.makePgRawName(pgId = ""),
                    pay_method   = PayMethod.kakaopay,
                    name         = productName,
                    merchant_uid = payMerchantUid,
//                                amount       = requireNotNull(price.toString()),
                    amount       = "100",
                    buyer_name   = buyerName
                )
            }
            SAMSUNG -> {
                IamPortRequest(
                    pg           = PG.nice.makePgRawName(pgId = ""),
                    pay_method   = PayMethod.samsung,
                    name         = productName,
                    merchant_uid = payMerchantUid,
//                                amount       = requireNotNull(price.toString()),
                    amount       = "100",
                    buyer_name   = buyerName
                )
            }
            else -> { throw Exception("no exist pay type") }
        }
    }

    private fun returnMerchantUid(): String
        = "$sellerName|${DateUtil.getCurrentTimeMillisForPay()}"


    companion object {
        const val PHONE = "phone"
        const val KAKAO = "kakao"
        const val SAMSUNG = "samsung"
    }

}
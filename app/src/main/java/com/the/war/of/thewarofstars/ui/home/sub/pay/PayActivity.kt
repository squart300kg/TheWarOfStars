package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.data.sdk.PG
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.ICallbackPaymentResult
import com.iamport.sdk.domain.core.Iamport
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.DialogType
import com.the.war.of.thewarofstars.contant.PayType
import com.the.war.of.thewarofstars.databinding.ActivityPayBinding
import com.the.war.of.thewarofstars.model.PayNotiItem
import com.the.war.of.thewarofstars.model.response.PayResponse
import com.the.war.of.thewarofstars.ui.dialog.PayProcessDialogFragment
import com.the.war.of.thewarofstars.ui.login.LoginViewModel
import com.the.war.of.thewarofstars.util.DateUtil
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat


class PayActivity: BaseActivity<ActivityPayBinding>(R.layout.activity_pay){

    private val TAG = "PayActivityLog"

    var dialog = PayProcessDialogFragment(this@PayActivity)

    private val loginViewModel: LoginViewModel by viewModel()
    private val payViewModel: PayViewModel by viewModel()

    private var isPersonalUsageTermsClicked = false
    private var isPayServiceTermsClicked    = false

    private var payType: PayType = PayType.PHONE
    private var productName: String? = null
    private var sellerName: String? = null
    private var sellerUID: String? = null
    private var buyerName: String? = null
    private var price: Long? = null

    private var isTermsChecked: Boolean = false

    private lateinit var payRequest : IamPortRequest
    private lateinit var payMerchantUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeImportSDK()

        initializeValues()

        initializeView()

        binding {

            payVm = payViewModel

            tvPayPhone.apply {
                setOnClickListener {
                    selectPayPhone()
                }
            }

            tvPayKakao.apply {
                setOnClickListener {
                    selectPaykakao()

                }
            }

            tvPaySamsung.apply {
                setOnClickListener {
                    selectPaySamsung()
                }
            }

            layoutTermsPersonalUsageTitle.apply {
                setOnClickListener {
                    when (isPersonalUsageTermsClicked) {
                        OPEN -> openPersonalUsageTerms()
                        CLOSE -> closePersonalUsageTerms()
                    }
                }
            }

            layoutTermsPayServiceTitle.apply {
                setOnClickListener {
                    when(isPayServiceTermsClicked) {
                        OPEN ->  openPayServiceTerms()
                        CLOSE -> closePayServiceTerms()
                    }
                }
            }

            tvPay.apply {
                setOnClickListener {
                    showProcessDialog()
                }
            }
        }

        observingForPay {
            payCompleteDetail.observe(this@PayActivity, { detail ->
                goToPayCompleteActivityAndSetDetail(detail)
            })
        }

        observingForLogin {
            isTermsChecked.observe(this@PayActivity, { isTermsChecked ->
                this@PayActivity.isTermsChecked = isTermsChecked
            })
        }
    }


    private fun openPayServiceTerms() {
        dataBinding.tvTermsPayServiceContent.visibility = View.VISIBLE
        dataBinding.tvTermsPayServiceContent.movementMethod = ScrollingMovementMethod()
        isPayServiceTermsClicked = true
    }

    private fun closePayServiceTerms() {
        dataBinding.tvTermsPayServiceContent.visibility = View.GONE
        isPayServiceTermsClicked = false
    }

    private fun openPersonalUsageTerms() {
        dataBinding.tvTermsPersonalUsageContent.visibility = View.VISIBLE
        dataBinding.tvTermsPersonalUsageContent.movementMethod = ScrollingMovementMethod()
        isPersonalUsageTermsClicked = true
    }

    private fun closePersonalUsageTerms() {
        dataBinding.tvTermsPersonalUsageContent.visibility = View.GONE
        isPersonalUsageTermsClicked = false
    }

    private fun selectPaySamsung() {
        dataBinding.tvPayPhone.isSelected = false
        dataBinding.tvPayKakao.isSelected = false
        dataBinding.tvPaySamsung.isSelected = true
        payType = PayType.SAMSUNG
    }

    private fun selectPaykakao() {
        dataBinding.tvPayPhone.isSelected = false
        dataBinding.tvPayKakao.isSelected = true
        dataBinding.tvPaySamsung.isSelected = false
        payType = PayType.KAKAO
    }

    private fun selectPayPhone() {
        dataBinding.tvPayPhone.isSelected = true
        dataBinding.tvPayKakao.isSelected = false
        dataBinding.tvPaySamsung.isSelected = false
        payType = PayType.PHONE
    }

    private fun initializeImportSDK()
        = Iamport.init(this)


    private fun showProcessDialog() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (dialog.isAdded) {
            fragmentTransaction.remove(dialog)
            dialog = PayProcessDialogFragment(this@PayActivity)
        }
        dialog.show(fragmentManager, DialogType.PAY_PROCESS_DIALOG.type)
        fragmentManager.executePendingTransactions()
        dialog.dialog?.setOnDismissListener(object: DialogInterface,
            DialogInterface.OnDismissListener {
            override fun onDismiss(p0: DialogInterface?) {

                if (isTermsChecked) {
                    startPayProcess()
                }

            }
            override fun dismiss() { }
            override fun cancel() { }

        })
    }

    private fun startPayProcess() {
        initializeMerchantUid()

        initializePayRequest()

        requestPay()
    }

    private fun initializeMerchantUid() {
        payMerchantUid = "$sellerName|${DateUtil.getCurrentTimeMillisForPay()}"
    }

    private fun initializePayRequest() {
        when (payType) {
            PayType.PHONE -> {
                payRequest = IamPortRequest(
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
            PayType.KAKAO -> {
                payRequest = IamPortRequest(
                    pg           = PG.nice.makePgRawName(pgId = ""),
                    pay_method   = PayMethod.kakaopay,
                    name         = productName,
                    merchant_uid = payMerchantUid,
//                                amount       = requireNotNull(price.toString()),
                    amount       = "100",
                    buyer_name   = buyerName
                )
            }
            PayType.SAMSUNG -> {
                payRequest = IamPortRequest(
                    pg           = PG.nice.makePgRawName(pgId = ""),
                    pay_method   = PayMethod.samsung,
                    name         = productName,
                    merchant_uid = payMerchantUid,
//                                amount       = requireNotNull(price.toString()),
                    amount       = "100",
                    buyer_name   = buyerName
                )
            }
        }
    }

    private fun requestPay() {
        Iamport.payment(getString(R.string.import_franchisee_code),
            iamPortRequest = payRequest,
            approveCallback = { },
            paymentResultCallback = {
                /* 최종 결제결과 콜백 함수. */
                callBackListener.result(it)

            })
    }

    private val callBackListener = object : ICallbackPaymentResult {
        override fun result(iamPortResponse: IamPortResponse?) {
            val resJson = GsonBuilder().setPrettyPrinting().create().toJson(iamPortResponse)

            Log.i(TAG, "callBackListener1\n $resJson")

            if (iamPortResponse != null) {

                Log.i(TAG, "callBackListener2\n $resJson")

                val jsonObject = JSONObject(resJson)
                val isSuccess: Boolean?
                val merchant_uid = jsonObject.getString("merchant_uid")

                when (payType) {
                    PayType.PHONE -> {
                        isSuccess = jsonObject.getString("success").toBoolean()
                    }
                    PayType.KAKAO -> {
                        isSuccess = jsonObject.getString("imp_success").toBoolean()
                    }
                    PayType.SAMSUNG -> {
                        isSuccess = jsonObject.getString("success").toBoolean()
                    }
                }

                Log.i(TAG, "callBackListener3\n " +
                        "isSuccess : $isSuccess\n " +
                        "merchant_uid : $merchant_uid")

                Log.i(TAG, "callBackListener4\n " +
                        "merchandUID : $merchant_uid\n " +
                        "to : $sellerUID\n " +
                        "from : ${Application.instance?.userUID}\n " +
                        "content : ${dataBinding.etRequestBeforeGame.text.toString()}\n " +
                        "price : ${price.toString()}")

                when (isSuccess) {
                    true -> {
                        payViewModel.sendPayNotification(
                            PayNotiItem(
                                to = sellerUID,
                                from = Application.instance?.userUID,
                                content = dataBinding.etRequestBeforeGame.text.toString(),
                                price = price.toString()
                            )
                        )
                    }
                    false -> {
                        val errorMessage = jsonObject.getString("error_msg")
                        Toast.makeText(this@PayActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    private fun goToPayCompleteActivityAndSetDetail(detail: PayResponse) {
        Intent(this@PayActivity, PayCompleteActivity::class.java).apply {
            putExtra("gamerUID", detail.gamer.gamerUID)
            putExtra("gamerName", detail.gamer.gamerName)
            putExtra("gamerCode", detail.gamer.gamerCode)
            putExtra("gamerTribe", detail.gamer.gamerTribe)
            putExtra("gamerID", detail.gamer.gamerID)

            putExtra("userUID", detail.user.userUID)
            putExtra("userNickname", detail.user.userNickname)
            putExtra("userCode", detail.user.userCode)
            putExtra("userTribe", detail.user.userTribe)
            putExtra("userID", detail.user.userID)

            putExtra("content", detail.content)
            putExtra("price", detail.price)
            putExtra("payDate", detail.payDate)

            putExtra("payUID", detail.payUID)
            putExtra("payStatus", detail.payStatus)
            finish()
            startActivity(this)
        }
    }

    private fun initializeValues() {
        productName = intent.getStringExtra("productName")
        sellerName  = intent.getStringExtra("sellerName")
        sellerUID   = intent.getStringExtra("sellerUID")
        buyerName   = intent.getStringExtra("buyerName")
        price       = intent.getLongExtra("price", 0)
    }

    private fun initializeView() {
        dataBinding.tvPayPhone.isSelected = true
        dataBinding.etEmail.setText(Application.instance?.userEmail)
        dataBinding.etGameID.setText(Application.instance?.userGameID)
        dataBinding.tvPay.text = DecimalFormat("###,###").format(price) + "원 결제 진행하기"

        initializeUnderLine()

    }

    private fun initializeUnderLine() {
        val content = SpannableString(dataBinding.tvGuideFeedbackEmail.text.toString())
        content.setSpan(UnderlineSpan(), 7, 24, 0)
        dataBinding.tvGuideFeedbackEmail.text = content
    }

    private fun observingForLogin(action: LoginViewModel.() -> Unit) {
        loginViewModel.run(action)
    }

    private fun observingForPay(action: PayViewModel.() -> Unit) {
        payViewModel.run(action)
    }

    companion object {
        const val OPEN = false
        const val CLOSE = true
    }

}
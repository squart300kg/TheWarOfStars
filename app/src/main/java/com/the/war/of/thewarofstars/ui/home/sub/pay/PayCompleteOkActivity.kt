package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.MainActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.PayProcessType
import com.the.war.of.thewarofstars.contant.UserType
import com.the.war.of.thewarofstars.databinding.ActivityPayCompleteOkBinding
import com.the.war.of.thewarofstars.util.DateUtil
import org.koin.android.ext.android.bind
import java.text.DecimalFormat

class PayCompleteOkActivity: BaseActivity<ActivityPayCompleteOkBinding>(R.layout.activity_pay_complete_ok) {

    private var gamerUID: String? = null
    private var gamerName: String? = null
    private var gamerCode: String? = null
    private var gamerTribe: String? = null
    private var gamerID: String? = null

    private var userUID: String? = null
    private var userNickname: String? = null
    private var userCode: String? = null
    private var userTribe: String? = null
    private var userID: String? = null

    private var price: String? = null
    private var payDate: String? = null
    private var payStatus: String? = null
    private var payUID: String? = null

    private val TAG = "PayCompleteOkActLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeValues()

        initializeView()

        binding {
            layoutOk.apply {
                setOnClickListener {

                    if (isReviewEmpty()) {
                        showToast(getString(R.string.pay_complete_ok_please_write_review))
                    } else {
                        goMain()
                    }
                }
            }
        }
    }

    private fun isReviewEmpty(): Boolean {
        return dataBinding.etReview.text.isNullOrEmpty()
    }

    private fun initializeView() {
        dataBinding.tvGamerInfo.text = "${gamerName}"
        dataBinding.tvGamerCode.text = gamerCode
        dataBinding.tvGamerTribe.text = gamerTribe
        dataBinding.tvGamerId.text = gamerID

        dataBinding.tvAmatureInfo.text = "${userNickname}"
        dataBinding.tvAmatureCode.text = userCode
        dataBinding.tvAmatureTribe.text = userTribe
        dataBinding.tvAmatureId.text = userID

        dataBinding.tvPrice.text = DecimalFormat("###,###").format(price?.toLong()) + "원"
        dataBinding.tvPayDate.text = DateUtil.getDateFromTimeMillis(payDate?.toLong())
        dataBinding.tvPayStatus.text = if (payStatus == PayProcessType.PAY_YET.type) "인수확인 전" else if (payStatus == PayProcessType.PAY_SUCCESS.type) "인수확인 완료" else "인수 취소"

        val userType = Application.instance?.userType
        when (userType) {
            UserType.USER.type -> {
                dataBinding.layoutScore.visibility = View.VISIBLE
                dataBinding.tvReviewTitle.visibility = View.VISIBLE
                dataBinding.etReview.visibility = View.VISIBLE
            }
            UserType.GAMER.type -> {
                dataBinding.layoutScore.visibility = View.GONE
                dataBinding.tvReviewTitle.visibility = View.GONE
                dataBinding.etReview.visibility = View.GONE
            }
        }

    }

    private fun initializeValues() {
        gamerUID = intent.getStringExtra("gamerUID")
        gamerName = intent.getStringExtra("gamerName")
        gamerCode = intent.getStringExtra("gamerCode")
        gamerTribe = intent.getStringExtra("gamerTribe")
        gamerID = intent.getStringExtra("gamerID")

        userUID = intent.getStringExtra("userUID")
        userNickname = intent.getStringExtra("userNickname")
        userCode = intent.getStringExtra("userCode")
        userTribe = intent.getStringExtra("userTribe")
        userID = intent.getStringExtra("userID")

        price   = intent.getStringExtra("price")
        payDate = intent.getStringExtra("payDate")
        payStatus = intent.getStringExtra("payStatus")
        payUID = intent.getStringExtra("payUID")

        Log.i(
            TAG,
            "initializeValues\n " +
                    "gamerUID : $gamerUID\n " +
                    "gamerName : $gamerName\n " +
                    "gamerCode : $gamerCode\n " +
                    "gamerTribe : $gamerTribe\n " +
                    "gamerID : $gamerID\n " +

                    "userUID : $userUID\n " +
                    "userNickname : $userNickname\n " +
                    "userCode : $userCode\n " +
                    "userTribe : $userTribe\n " +
                    "userID : $userID\n " +

                    "price : $price\n " +
                    "payDate : $payDate\n " +
                    "payStatus : $payStatus\n " +
                    "payUID : $payUID")
    }

    private fun goMain() {
        val intent = Intent(this@PayCompleteOkActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
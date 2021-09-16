package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.DialogType
import com.the.war.of.thewarofstars.contant.PayProcessType
import com.the.war.of.thewarofstars.contant.UserType
import com.the.war.of.thewarofstars.databinding.ActivityPayCompleteBinding
import com.the.war.of.thewarofstars.ui.dialog.PayCancelDialogFragment
import com.the.war.of.thewarofstars.ui.dialog.PayOkDialogFragment
import com.the.war.of.thewarofstars.ui.home.HomeViewModel
import com.the.war.of.thewarofstars.ui.home.sub.question.QuestionActivity
import com.the.war.of.thewarofstars.util.DateUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class PayCompleteActivity: BaseActivity<ActivityPayCompleteBinding>(R.layout.activity_pay_complete) {

    private val payCompleteViewModel: PayCompleteViewModel by viewModel()

    private var isPaySelected: Boolean = true

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

    private var content: String? = null
    private var price: String? = null
    private var payDate: String? = null
    private var payStatus: String? = null

    var okDialog     = PayOkDialogFragment(this@PayCompleteActivity)
    var cancelDialog = PayCancelDialogFragment(this@PayCompleteActivity)

    private val TAG = "PayCompleteActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeValues()

        initializeView()

        binding {

            tvChatting.setOnClickListener {
                goToChattingActivity()
            }

            tvPayOk.apply {
                // 기본설정
                isSelected = true

                setOnClickListener {

                    when (isPaySelected) {
                        true -> {
                            showOkDialog()
                        }
                        false -> {
                            selectOkButton()
                        }
                    }

                }
            }

            tvPayCancel.apply {
                setOnClickListener {
                    when (isPaySelected) {
                        false -> {
                            showCancelDialog()
                        }
                        true -> {
                            selectCancelButton()

                        }
                    }
                }
            }
        }

        observing {
            isClickedOk.observe(this@PayCompleteActivity, { isClickedOk ->
                when (isClickedOk) {
                    true -> {
                        Intent(this@PayCompleteActivity, PayCompleteOkActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
                }
            })
        }

    }

    private fun goToChattingActivity() {
        Intent(this@PayCompleteActivity, QuestionActivity::class.java).apply {
            when (Application.instance?.userType) {
                UserType.GAMER.type -> {
                    putExtra("senderName", userNickname)
                    putExtra("senderUID", userUID)
                }
                UserType.USER.type -> {
                    putExtra("senderName", gamerName)
                    putExtra("senderUID", gamerUID)
                }
            }
            startActivity(this)
        }
    }

    private fun selectCancelButton() {
        dataBinding.tvPayCancel.isSelected = true
        dataBinding.tvPayOk.isSelected = false

        isPaySelected = false
    }

    private fun selectOkButton() {
        dataBinding.tvPayOk.isSelected = true
        dataBinding.tvPayCancel.isSelected = false

        isPaySelected = true
    }

    private fun showCancelDialog() {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        if (cancelDialog.isAdded) {
            ft.remove(okDialog)
            cancelDialog = PayCancelDialogFragment(this@PayCompleteActivity)
        }
        cancelDialog.show(supportFragmentManager, DialogType.PAY_CANCEL_DIALOG.type)
        supportFragmentManager.executePendingTransactions()
        cancelDialog.dialog?.setOnDismissListener(object: DialogInterface,
            DialogInterface.OnDismissListener {
            override fun onDismiss(p0: DialogInterface?) {
//                onBackPressed()
            }
            override fun dismiss() { }
            override fun cancel() { }

        })
    }

    private fun showOkDialog() {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        if (okDialog.isAdded) {
            ft.remove(okDialog)
            okDialog = PayOkDialogFragment(this@PayCompleteActivity)
        }
        okDialog.show(supportFragmentManager, DialogType.PAY_OK_DIALOG.type)
        supportFragmentManager.executePendingTransactions()
        okDialog.dialog?.setOnDismissListener(object: DialogInterface,
            DialogInterface.OnDismissListener {
            override fun onDismiss(p0: DialogInterface?) {
//                onBackPressed()
            }
            override fun dismiss() { }
            override fun cancel() { }

        })
    }

    private fun initializeView() {

        initializeUnderLine()
        dataBinding.tvGamerInfo.text = "${gamerName}"
        dataBinding.tvGamerCode.text = gamerCode
        dataBinding.tvGamerTribe.text = gamerTribe
        dataBinding.tvGamerId.text = gamerID

        dataBinding.tvAmatureInfo.text = "${userNickname}"
        dataBinding.tvAmatureCode.text = userCode
        dataBinding.tvAmatureTribe.text = userTribe
        dataBinding.tvAmatureId.text = userID

        dataBinding.tvRequestBeforeGameContent.text = content
        dataBinding.tvPrice.text = DecimalFormat("###,###").format(price?.toLong()) + "원"
        dataBinding.tvPayDate.text = DateUtil.getDateFromTimeMillis(payDate?.toLong())
        dataBinding.tvPayStatus.text = if (payStatus == PayProcessType.PAY_YET.type) "인수확인 전" else if (payStatus == PayProcessType.PAY_SUCCESS.type) "인수확인 완료" else "인수 취소"

        val userType = Application.instance?.userType
        when (userType) {
            UserType.USER.type -> {
                dataBinding.tvPayOkGuide.visibility = View.VISIBLE
                dataBinding.tvPayOkGuideDownArrow.visibility = View.VISIBLE
                dataBinding.layoutComplete.visibility = View.VISIBLE
                dataBinding.tvChatting.text = "${gamerName}님과 채팅하러 가기"
            }
            UserType.GAMER.type -> {
                dataBinding.tvPayOkGuide.visibility = View.GONE
                dataBinding.tvPayOkGuideDownArrow.visibility = View.GONE
                dataBinding.layoutComplete.visibility = View.GONE
                dataBinding.tvChatting.text = "${userNickname}님과 채팅하러 가기"
            }
        }
    }

    private fun initializeUnderLine() {
        val content = SpannableString(dataBinding.tvPayOkGuide.text.toString())
        content.setSpan(UnderlineSpan(), 18, 35, 0)
        dataBinding.tvPayOkGuide.text = content
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

        content = intent.getStringExtra("content")
        price   = intent.getStringExtra("price")
        payDate = intent.getStringExtra("payDate")
        payStatus = intent.getStringExtra("payStatus")

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

                "content : $content\n " +
                "price : $price\n " +
                "payDate : $payDate\n " +
                "payStatus : $payStatus\n ")
    }

    private fun observing(action: PayCompleteViewModel.() -> Unit) {
        payCompleteViewModel.run(action)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PayActivity::class.java)
    }

}
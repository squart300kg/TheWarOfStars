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
import com.the.war.of.thewarofstars.contant.UserType
import com.the.war.of.thewarofstars.databinding.ActivityPayCompleteBinding
import com.the.war.of.thewarofstars.ui.dialog.PayCancelDialogFragment
import com.the.war.of.thewarofstars.ui.dialog.PayOkDialogFragment
import com.the.war.of.thewarofstars.ui.home.HomeViewModel
import com.the.war.of.thewarofstars.ui.home.sub.question.QuestionActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class PayCompleteActivity: BaseActivity<ActivityPayCompleteBinding>(R.layout.activity_pay_complete) {

    private val payCompleteViewModel: PayCompleteViewModel by viewModel()

    private var isPaySelected: Boolean = true

    private var price: Long? = null
    private var content: String? = null
    private var payDate: String? = null

    var okDialog     = PayOkDialogFragment(this@PayCompleteActivity)
    var cancelDialog = PayCancelDialogFragment(this@PayCompleteActivity)

    private val TAG = "PayCompleteActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeValues()

        initializeView()


        binding {

            tvChatting.setOnClickListener {
                Intent(this@PayCompleteActivity, QuestionActivity::class.java).apply {
//                    intent.putExtra("senderName", name)
//                    intent.putExtra("senderUID", uID)
                    startActivity(this)
                }
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

        val userType = Application.instance?.userType
        when (userType) {
            UserType.USER.type -> {
                dataBinding.tvPayOkGuide.visibility = View.VISIBLE
                dataBinding.tvPayOkGuideDownArrow.visibility = View.VISIBLE
                dataBinding.layoutComplete.visibility = View.VISIBLE
                dataBinding.tvChatting.text = getString(R.string.pay_ok_chatting_to_gamer)
                dataBinding.layoutPayStatus.visibility = View.GONE

            }
            UserType.GAMER.type -> {
                // TODO 데이터 연동시 지울 것
                dataBinding.tvRequestBeforeGameContent.text = "정석 운영플레이 실력을 쌓고 싶습니다"

                dataBinding.tvPayOkGuide.visibility = View.GONE
                dataBinding.tvPayOkGuideDownArrow.visibility = View.GONE
                dataBinding.layoutComplete.visibility = View.GONE
                dataBinding.tvChatting.text = getString(R.string.pay_ok_chatting_to_amature)
                dataBinding.layoutPayStatus.visibility = View.VISIBLE
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
        content = intent.getStringExtra("content")
        price   = intent.getLongExtra("price", 0)

        Log.i(TAG, "initializeValues\n " +
                "payDate : $payDate\n " +
                "request : $content\n " +
                "price : $price")

        dataBinding.tvRequestBeforeGameContent.text = content
        dataBinding.tvPrice.text                    = DecimalFormat("###,###").format(price) + "원"
        dataBinding.tvPayDate.text                  = payDate
    }

    private fun observing(action: PayCompleteViewModel.() -> Unit) {
        payCompleteViewModel.run(action)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PayActivity::class.java)
    }

}
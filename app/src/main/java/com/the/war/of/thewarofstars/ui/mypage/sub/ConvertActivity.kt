package com.the.war.of.thewarofstars.ui.mypage.sub

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.DialogType
import com.the.war.of.thewarofstars.databinding.ActivityConvertBinding
import com.the.war.of.thewarofstars.ui.dialog.ConvertDialogFragment
import com.the.war.of.thewarofstars.ui.dialog.PayOkDialogFragment

class ConvertActivity: BaseActivity<ActivityConvertBinding>(R.layout.activity_convert) {

    var dialog     = ConvertDialogFragment(this@ConvertActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            ivBack.apply {
                setOnClickListener {
                    onBackPressed()
                }
            }

            rvConvertHistory.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                val convertAdapter      = ConvertAdapter(this@ConvertActivity)

                layoutManager = linearLayoutManager
                adapter       = convertAdapter
            }

            layoutRequestConvert.apply {
                setOnClickListener {
                    showConvertDialog()
                }
            }
        }
    }

    private fun showConvertDialog() {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        if (dialog.isAdded) {
            ft.remove(dialog)
            dialog = ConvertDialogFragment(this@ConvertActivity)
        }
        dialog.show(supportFragmentManager, DialogType.CONVERT_DIALOG.type)
        supportFragmentManager.executePendingTransactions()
        dialog.dialog?.setOnDismissListener(object: DialogInterface,
            DialogInterface.OnDismissListener {
            override fun onDismiss(p0: DialogInterface?) {
//                onBackPressed()
            }
            override fun dismiss() { }
            override fun cancel() { }

        })
    }
}
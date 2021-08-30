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
import com.the.war.of.thewarofstars.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConvertActivity: BaseActivity<ActivityConvertBinding>(R.layout.activity_convert) {

    var dialog = ConvertDialogFragment(this@ConvertActivity)

    private val convertViewModel: ConvertViewModel by viewModel()

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
//                    showConvertDialog()
                    Intent(this@ConvertActivity, ConvertInputActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            }
        }

        observing {
            isConvertClicked.observe(this@ConvertActivity, { isConvertClicked ->
                when (isConvertClicked) {
                    true -> {

                    }
                    false -> {

                    }
                }
            })
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

    private fun observing(action: ConvertViewModel.() -> Unit) {
        convertViewModel.run(action)
    }
}
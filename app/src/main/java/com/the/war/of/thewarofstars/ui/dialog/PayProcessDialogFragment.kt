package com.the.war.of.thewarofstars.ui.dialog

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.PayProcessDialogBinding

/**
 * Created by sangyoon on 2021/07/27
 */
class PayProcessDialogFragment(
    val activity: Activity
) : DialogFragment()  {
    private lateinit var dataBinding: PayProcessDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // remove dialog title
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.pay_process_dialog, container, false)

        dataBinding.layoutNoticeOk.apply {
            setOnClickListener {
                when (dataBinding.cbNoticeOk.isChecked) {
                    true -> {
                        dataBinding.cbNoticeOk.isChecked = false
                    }
                    false -> {
                        dataBinding.cbNoticeOk.isChecked = true
                    }
                }
            }
        }

        dataBinding.layoutOk.apply {
            setOnClickListener {
                val isTermsChecked = dataBinding.cbNoticeOk.isChecked

                if (isTermsChecked) {
                    dialog?.dismiss()
                }

                else {
                    Toast.makeText(activity, getString(R.string.dialog_pay_process_please_check), Toast.LENGTH_LONG).show()
                }
            }

        }


        return dataBinding.root
    }

    override fun onDetach() {
        super.onDetach()
//        questionViewModel.isSend(true)
    }

}
package com.the.war.of.thewarofstars.ui.dialog

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.PayCancelDialogBinding

/**
 * Created by sangyoon on 2021/07/27
 */
class PayCancelDialogFragment(
    activity: Activity
) : DialogFragment()  {
    private lateinit var dataBinding: PayCancelDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // remove dialog title
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.pay_cancel_dialog, container, false)

        dataBinding.tvNo.apply {
            setOnClickListener {
                dialog?.dismiss()
            }

        }

        dataBinding.tvOk.apply {
            setOnClickListener {

            }

        }

        return dataBinding.root
    }

    override fun onDetach() {
        super.onDetach()
//        questionViewModel.isSend(true)
    }

}
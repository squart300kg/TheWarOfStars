package com.the.war.of.thewarofstars.ui.dialog

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ConvertDialogBinding
import com.the.war.of.thewarofstars.ui.login.LoginViewModel
import com.the.war.of.thewarofstars.ui.mypage.sub.ConvertViewModel
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by sangyoon on 2021/07/27
 */
class ConvertDialogFragment(
    val activity: Activity
) : DialogFragment()  {
    private lateinit var dataBinding: ConvertDialogBinding

    private val convertViewModel: ConvertViewModel by sharedViewModel()

    private val TAG = "ConvertDialogFragmentLog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // remove dialog title
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.convert_dialog, container, false)

        dataBinding.etInput.apply {
            addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null) {
                        val numberFormat = NumberFormat.getInstance().apply { isGroupingUsed = false }
                        val commaFormat  = DecimalFormat("###,###")

                        val point      = if (dataBinding.etInput.text.toString() == "") 0 else dataBinding.etInput.text.toString().toInt()
                        val cashDouble = numberFormat.format(point * 0.8)
                        val cashString = cashDouble.toString()
                        val cashInt    = cashString.split('.')[0]
                        val cashResult = commaFormat.format(cashInt.toInt())

                        Log.i(TAG, "point : $point\n " +
                                "cash : $cashDouble\n " +
                                "cash_string: $cashString\n " +
                                "cash_result : $cashInt\n " +
                                "price : $cashResult 원")

                        dataBinding.tvCash.text = cashResult+"원"
                    } else {
                        dataBinding.tvCash.text = "0원"
                    }
                }
            })
        }

        dataBinding.tvRequestConvert.apply {
            setOnClickListener {
                convertViewModel.convertClick()
                dismiss()
            }
        }



        return dataBinding.root
    }

    override fun onDetach() {
        super.onDetach()
//        questionViewModel.isSend(true)
    }

}
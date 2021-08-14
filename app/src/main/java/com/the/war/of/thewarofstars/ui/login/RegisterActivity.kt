package com.the.war.of.thewarofstars.ui.login

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.TribeType
import com.the.war.of.thewarofstars.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private val registerViewModel: RegisterViewModel by viewModel()

    private lateinit var tribeType: TribeType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            registerVm = registerViewModel

            initializeSpinner(spinnerQuestionType)
        }

    }

    private fun initializeSpinner(spinnerQuestionType: Spinner) {
        val items = resources.getStringArray(R.array.tribe_type_array)
        spinnerQuestionType.adapter = ArrayAdapter(this@RegisterActivity, R.layout.spinner_item, items)
        spinnerQuestionType.dropDownVerticalOffset = DensityUtils.dpToPx(44)
        spinnerQuestionType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Toast.makeText(this@RegisterActivity, "종족을 선택해 주세요", Toast.LENGTH_LONG).show()
                    }
                    1 -> {
                        tribeType = TribeType.TERRAN
                        Toast.makeText(this@RegisterActivity, "테란", Toast.LENGTH_LONG).show()
                    }
                    2 -> {
                        tribeType = TribeType.ZERG
                        Toast.makeText(this@RegisterActivity, "저그", Toast.LENGTH_LONG).show()
                    }
                    3 -> {
                        tribeType = TribeType.PROTOSS
                        Toast.makeText(this@RegisterActivity, "프로토스", Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }

        }
    }
}
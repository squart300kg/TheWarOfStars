package com.the.war.of.thewarofstars.ui.login

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.TribeType
import com.the.war.of.thewarofstars.databinding.ActivityRegisterBinding
import com.the.war.of.thewarofstars.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private val registerViewModel: RegisterViewModel by viewModel()

    private lateinit var tribeType: TribeType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            registerVm = registerViewModel

            initializeSpinner(spinnerQuestionType)

            tvCheckDup.apply{
                setOnClickListener {
                    registerViewModel.isExistNickname(etNickname.text.toString())

                    hideKeyBoard(etNickname)
                }
            }
        }

        observing {
            isExistNickname.observe(this@RegisterActivity, { isExist ->
                when (isExist) {
                    true -> {
                        dataBinding.layoutInputNickname.setBackgroundResource(R.drawable.border_input_error)
                        dataBinding.tvNicknameMessage.visibility = View.VISIBLE
                        dataBinding.tvCheckDup.setTextColor(Color.parseColor("#ff6a1a"))
                        dataBinding.tvNicknameMessage.setTextColor(Color.parseColor("#ff3434"))
                        dataBinding.tvNicknameMessage.text = "사용중인 닉네임 입니다"
                    }

                    false -> {
                        dataBinding.layoutInputNickname.setBackgroundResource(R.drawable.border_chatting_input)
                        dataBinding.tvNicknameMessage.visibility = View.VISIBLE
                        dataBinding.tvCheckDup.setTextColor(Color.parseColor("#8f8f8f"))
                        dataBinding.tvNicknameMessage.setTextColor(Color.parseColor("#8f8f8f"))
                        dataBinding.tvNicknameMessage.text = "사용 가능한 닉네임 입니다"

                    }
                }
            })
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

    private fun hideKeyBoard(input: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(input.windowToken, 0)
        input.clearFocus()
    }

    private fun observing(action: RegisterViewModel.() -> Unit) {
        registerViewModel.run(action)
    }
}
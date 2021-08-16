package com.the.war.of.thewarofstars.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.MainActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.TribeType
import com.the.war.of.thewarofstars.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity: BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private val registerViewModel: RegisterViewModel by viewModel()

    private var tribeType: TribeType? = null

    private var isNicknameDupChecked = false
    private var isEmailDupChecked    = false
    private var isNicknameValidated  = false

    private val TAG = "RegisterActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            registerVm = registerViewModel

            initializeSpinner(spinnerQuestionType)

            tvEmailCheckDup.apply {
                setOnClickListener {

                    if (etEmail.text.isNullOrEmpty()) {
                        Toast.makeText(this@RegisterActivity, "이메일을 입력해 주세요", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    /**
                     * 이메일이 온전한 형태인지 검사하는 로직
                     * ex). hello@hello.com -> 됨
                     *      hello@@heoo     -> 안됨
                     */
                    if (!isEmailValidated()) {
                        Toast.makeText(this@RegisterActivity, "올바른 이메일을 입력해 주세요", Toast.LENGTH_LONG).show()
                        tvEmailMessage.visibility = View.GONE
                        return@setOnClickListener
                    }

                    registerViewModel.isExistEmail(etEmail.text.toString())

                }
            }

            tvNicknameCheckDup.apply{
                setOnClickListener {

                    if (etNickname.text.isNullOrEmpty()) {
                        Toast.makeText(this@RegisterActivity, "닉네임을 입력해 주세요", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    /**
                     * 닉네임이 온전한 한글인지 검사하는 로직
                     * ex). 안녕밍 -> 됨
                     *      안녕ㅇ -> 안됨
                     */
                    if (!isNicknameValidated()) {
                        Toast.makeText(this@RegisterActivity, "올바른 닉네임을 입력해 주세요", Toast.LENGTH_LONG).show()
                        tvNicknameMessage.visibility = View.GONE
                        return@setOnClickListener
                    }

                    registerViewModel.isExistNickname(etNickname.text.toString())

                    hideKeyBoard(etNickname)
                }
            }

            btNext.apply {
                setOnClickListener {

                    /**
                     * 0. 이메일이 공백인지 체크한다.
                     * 1. 비밀번호와 비밀번호 확인이 동일한지 체크한다.
                     * 2. 비밀번호가 6자리 이상인지 체크한다.
                     * 3. 닉네임 중복체크 여부를 확인한다.
                     * 4. 이메일 중복체크 여부를 확인한다.
                     * 5. 종족을 선택했는지 체크한다
                     */
                    if (isRegisterFormCorrect()) {
                        val email = dataBinding.etEmail.text.toString()
                        val password = dataBinding.etPassword.text.toString()
                        val fcmToken = Application.instance!!.userFcmToken
                        val nickname = dataBinding.etNickname.text.toString()
                        val tribe = tribeType

                        Application.instance?.firebaseStore
                            ?.collection("UserList")
                            ?.add(hashMapOf(
                                "email" to email,
                                "password" to password,
                                "fcmToken" to fcmToken,
                                "nickname" to nickname,
                                "tribe" to tribe
                            ))
                            ?.addOnSuccessListener { document ->

                                val uID = document.id

                                registerViewModel.saveAutoLogin(true, email, nickname ,uID, password, "USER")
                                Log.d(TAG, "회원가입 완료! id : ${document.id}")

                                val intent = MainActivity.newIntent(this@RegisterActivity).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                startActivity(intent)
                            }
                            ?.addOnFailureListener { e -> Log.w(TAG, "회원가입 에러!", e) }

                    }

                }
            }
        }

        observing {
            isExistNickname.observe(this@RegisterActivity, { isExist ->
                when (isExist) {
                    true -> {
                        dataBinding.layoutInputNickname.setBackgroundResource(R.drawable.border_input_error)
                        dataBinding.tvNicknameMessage.visibility = View.VISIBLE
                        dataBinding.tvNicknameCheckDup.setTextColor(Color.parseColor("#ff6a1a"))
                        dataBinding.tvNicknameMessage.setTextColor(Color.parseColor("#ff3434"))
                        dataBinding.tvNicknameMessage.text = "사용중인 닉네임 입니다"
                        isNicknameDupChecked = false
                    }

                    false -> {
                        dataBinding.layoutInputNickname.setBackgroundResource(R.drawable.border_chatting_input)
                        dataBinding.tvNicknameMessage.visibility = View.VISIBLE
                        dataBinding.tvNicknameCheckDup.setTextColor(Color.parseColor("#8f8f8f"))
                        dataBinding.tvNicknameMessage.setTextColor(Color.parseColor("#8f8f8f"))
                        dataBinding.tvNicknameMessage.text = "사용 가능한 닉네임 입니다"
                        isNicknameDupChecked = true

                    }
                }
            })

            isExistEmail.observe(this@RegisterActivity, { isExist ->
                when (isExist) {
                    true -> {
                        dataBinding.layoutInputEmail.setBackgroundResource(R.drawable.border_input_error)
                        dataBinding.tvEmailMessage.visibility = View.VISIBLE
                        dataBinding.tvEmailCheckDup.setTextColor(Color.parseColor("#ff6a1a"))
                        dataBinding.tvEmailMessage.setTextColor(Color.parseColor("#ff3434"))
                        dataBinding.tvEmailMessage.text = "사용중인 이메일 입니다"
                        isEmailDupChecked = false
                    }

                    false -> {
                        dataBinding.layoutInputEmail.setBackgroundResource(R.drawable.border_chatting_input)
                        dataBinding.tvEmailMessage.visibility = View.VISIBLE
                        dataBinding.tvEmailCheckDup.setTextColor(Color.parseColor("#8f8f8f"))
                        dataBinding.tvEmailMessage.setTextColor(Color.parseColor("#8f8f8f"))
                        dataBinding.tvEmailMessage.text = "사용 가능한 이메일 입니다"
                        isEmailDupChecked = true

                    }
                }
            })
        }



    }

    /**
     * 온전한 한글인지 검사하는 로직
     * ex). ㄴㅇㄹ -> 안됨
     *      헬로밍 -> 됨
     */
    private fun isNicknameValidated(): Boolean {
        val nickname = dataBinding.etNickname.text.toString()

        nickname.forEach { char ->
            Log.i(TAG, "char : $char")

            if (char.toString().matches(".*[a-zA-Z0-9가-힣]+.*".toRegex())) {
                Log.i(TAG, "온전한 한글")
            }
            else {
                Log.i(TAG, "온전하지 못한 한글")
                return false
            }
        }

        return true
    }

    private fun isEmailValidated(): Boolean {
        //1. 이메일 형식이 올바른지 체크한다.
        val email = dataBinding.etEmail.text.toString()
        if (!isEmail(email) || email.isNullOrEmpty()) {
            Toast.makeText(this@RegisterActivity, "올바른 이메일 형식을 입력해 주세요", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun isRegisterFormCorrect(): Boolean {

        // 0. 이메일이 공백인지 체크한다.
        val email = dataBinding.etEmail.text.toString()
        if (email.isNullOrEmpty()) {
            Toast.makeText(this@RegisterActivity, "이메일을 입력해 주세요", Toast.LENGTH_LONG).show()
            return false
        }

        //1. 비밀번호와 비밀번호 확인이 동일한지 체크한다.
        val password        = dataBinding.etPassword.text.toString()
        val passwordConfirm = dataBinding.etPasswordComfirm.text.toString()
        if (password != passwordConfirm || password.isNullOrEmpty() || passwordConfirm.isNullOrEmpty()) {
            Toast.makeText(this@RegisterActivity, "비밀번호를 올바르게 입력해 주세요", Toast.LENGTH_LONG).show()
            return false
        }

        //2. 비밀번호가 6자리 이상인지 체크한다.
        if (password.length < 5 || passwordConfirm.length < 5) {
            Toast.makeText(this@RegisterActivity, "비밀번호를 6자리 이상 입력해 주세요", Toast.LENGTH_LONG).show()
            return false
        }

        //3. 닉네임 중복체크 여부를 확인한다.
        if (!isNicknameDupChecked) {
            Toast.makeText(this@RegisterActivity, "닉네임 중복검사를 해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        //4. 이메일 중복체크 여부를 확인한다.
        if (!isEmailDupChecked) {
            Toast.makeText(this@RegisterActivity, "이메일 중복검사를 해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        //4. 종족을 선택했는지 체크한다
        if (tribeType == null) {
            Toast.makeText(this@RegisterActivity, "종족을 선택해 주세요", Toast.LENGTH_LONG).show()
            return false
        }



        return true
    }

    private fun isEmail(email: String?): Boolean {
        var returnValue = false
        val regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(email)
        if (m.matches()) {
            returnValue = true
        }
        return returnValue
    }

    private fun initializeSpinner(spinnerQuestionType: Spinner) {
        val items = resources.getStringArray(R.array.tribe_type_array)
        spinnerQuestionType.adapter = ArrayAdapter(this@RegisterActivity, R.layout.spinner_item, items)
        spinnerQuestionType.dropDownVerticalOffset = DensityUtils.dpToPx(44)
        spinnerQuestionType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> { }
                    1 -> {
                        tribeType = TribeType.TERRAN
                    }
                    2 -> {
                        tribeType = TribeType.ZERG
                    }
                    3 -> {
                        tribeType = TribeType.PROTOSS
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
package com.the.war.of.thewarofstars.ui.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.MainActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.NotiType
import com.the.war.of.thewarofstars.contant.NotiInfo
import com.the.war.of.thewarofstars.databinding.ActivityLoginBinding
import com.the.war.of.thewarofstars.util.NaverLogin
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel: LoginViewModel by viewModel()

    val TAG = "LoginActivityLog"

    private val startActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result?.resultCode == Activity.RESULT_OK) {
                goNext(MainActivity::class.java)
        }
    }

    /**
     * 로그인 프로세스
     *1. 네이버 서버로부터 사용자 정보를 가져온다.
     *2. 사용자 정보를 사용해 회원가입 여부를 검사한다.
     * 2.1. 회원가입을 했다면 메인페이지로 넘겨준다.
     * 2.2. 회원가입을 안했다면 서버에 회원정보를 저장하고 메인페이지로 넘겨준다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 알림을 통해 들어왔는지 체크
         */
        checkForNotification()

        binding {
            videoView.apply{
                setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.daum_star_league))
                start()
            }

            naverLoginButton.apply {
                setOnClickListener { NaverLogin.startNaverLogin(this@LoginActivity, loginViewModel) }
            }

            emailLoginButton.apply {
                setOnClickListener {
                    startActivityResult.launch(Intent(this@LoginActivity, EmailLoginActivity::class.java))
                }
            }
            videoView.apply {
                setOnClickListener{ NaverLogin.startNaverLogout(this@LoginActivity) }
            }
        }

        observing {
            email.observe(this@LoginActivity, { email ->
                Log.i(TAG, "observe email : $email")

            })

            nickname.observe(this@LoginActivity, { nickname ->
                Log.i(TAG, "observe nickname : $nickname")

            })

            naverApiStatus.observe(this@LoginActivity, { status ->
                when (status) {
                    true -> {
                        loginViewModel.isRegister(loginViewModel.email.value)
                    }
                    false -> { }
                }
            })

            isRegister.observe(this@LoginActivity, { isRegister ->
                when (isRegister) {
                    true -> {
                        /**
                         * 회원이므로 메인페이지로 이동한다.
                         */
                        Application.instance?.userEmail = loginViewModel.email.value
                        Application.instance?.userName  = loginViewModel.nickname.value
                        Application.instance?.userUID   = loginViewModel.uID.value
                        goNext(MainActivity::class.java)
                    }
                    false -> {
                        /**
                         * 회원이 아니므로, 회원가입 후, 메인페이지로 이동한다.
                         */
                        loginViewModel.registerUser()
                    }
                }
            })
        }

    }

    override fun onStart() {
        super.onStart()

        /**
         * 자동로그인이 설정되어 있는지를 체크
         */
        isAutoLoginEnabled()
    }

    private fun checkForNotification() {
        val extras = intent?.extras
        Log.i(TAG, "extra : $extras")
        if (extras != null) {
            when (extras[NotiInfo.NOTI_TYPE.type]) {
                NotiType.CHATTING.type -> {
                    Log.i(TAG,
                        "MessageType.CHATTING")
                    val senderName = intent.getStringExtra(NotiInfo.SENDER_NAME.type)
                    val senderUID  = intent.getStringExtra(NotiInfo.SENDER_UID.type)

                    MainActivity.newIntent(this).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra(NotiInfo.SENDER_NAME.type, senderName)
                        putExtra(NotiInfo.SENDER_UID.type, senderUID)
                        putExtra(NotiInfo.NOTI_TYPE.type, NotiType.CHATTING.type)
                        Log.i(TAG, "senderUID : $senderUID, senderName : $senderName")
                        startActivity(this)
                        finish()
                    }
                }
                NotiType.PAY.type -> {
                    Log.i(TAG,
                    "MessageType.PAY")
                    MainActivity.newIntent(this).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra(NotiInfo.NOTI_TYPE.type, NotiType.PAY.type)
                        startActivity(this)
                        finish()
                    }
                }
            }

//            if (extras.containsKey(NotiInfo.NOTI_TYPE.type)   &&
//                extras.containsKey(NotiInfo.SENDER_UID.type)  &&
//                extras.containsKey(NotiInfo.SENDER_NAME.type) &&
//                extras.containsKey(NotiInfo.SENDER_TYPE.type)) {
//
//                val senderName = intent.getStringExtra(NotiInfo.SENDER_NAME.type)
//                val senderUID  = intent.getStringExtra(NotiInfo.SENDER_UID.type)
//
//                val intent = MainActivity.newIntent(this).apply {
//                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    putExtra(NotiInfo.SENDER_NAME.type, senderName)
//                    putExtra(NotiInfo.SENDER_UID.type, senderUID)
//                }
//                Log.i(TAG, "senderUID : $senderUID, senderName : $senderName")
//                startActivity(intent)
//                finish()
//            }

        }
    }

    private fun isAutoLoginEnabled() {
        val status = loginViewModel.getAutoLoginStatus().toBoolean()
        Log.i(TAG, "autoLoginStatus : $status")
        if (status) {
            val userInfoMap = loginViewModel.getAutoLoginUserInfo()
            Application.instance?.userEmail  = userInfoMap["email"]
            Application.instance?.userName   = userInfoMap["name"]
            Application.instance?.userUID    = userInfoMap["uID"]
            Application.instance?.userType   = userInfoMap["type"]
            Application.instance?.userTribe  = userInfoMap["tribe"]
            Application.instance?.userGameID = userInfoMap["gameID"]
            Toast.makeText(this, "자동로그인 성공!\n\n " +
                    "email : ${Application.instance?.userEmail},\n " +
                    "name : ${Application.instance?.userName},\n " +
                    "uID : ${Application.instance?.userUID}, \n " +
                    "fcmToken : ${Application.instance?.userFcmToken}\n, " +
                    "userType : ${Application.instance?.userType}\n " +
                    "tribe : ${Application.instance?.userTribe}\n " +
                    "gameID : ${Application.instance?.userGameID}",
                Toast.LENGTH_LONG).show()

            Log.i(TAG, "isAutoLoginEnabled\n " +
                    "email : ${Application.instance?.userEmail}\n" +
                    "name : ${Application.instance?.userName}\n" +
                    "uID : ${Application.instance?.userUID}\n" +
                    "fcmToken : ${Application.instance?.userFcmToken}\n" +
                    "userType : ${Application.instance?.userType} \n" +
                    "tribe : ${Application.instance?.userTribe} \n" +
                    "gameID : ${Application.instance?.userGameID}")
            goNext(MainActivity::class.java)
        } else {
            Toast.makeText(this, "자동로그인 실패!\n\n email : ${Application.instance?.userEmail},\n name : ${Application.instance?.userName},\n uID : ${Application.instance?.userUID}, \n fcmToken : ${Application.instance?.userFcmToken}, userType : ${Application.instance?.userType}", Toast.LENGTH_LONG).show()
        }
    }

    private fun observing(action: LoginViewModel.() -> Unit) {
        loginViewModel.run(action)
    }

    private fun goNext(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }



}




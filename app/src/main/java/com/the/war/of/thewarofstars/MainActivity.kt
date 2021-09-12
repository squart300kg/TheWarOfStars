package com.the.war.of.thewarofstars

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
//import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.the.war.of.thewarofstars.contant.NotiType
import com.the.war.of.thewarofstars.contant.NotiInfo
import com.the.war.of.thewarofstars.ui.home.sub.pay.PayCompleteActivity
import com.the.war.of.thewarofstars.ui.home.sub.question.QuestionActivity
import com.the.war.of.thewarofstars.ui.navigation.setupWithNavController
import com.the.war.of.thewarofstars.util.BackButtonCloseHandler


class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    private val backButtonCloseHandler = BackButtonCloseHandler(this)

    private val TAG = "MainActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 알림을 통해 들어왔는지 체크
         */
        checkForNotification()

        if (savedInstanceState == null) {
            setUpBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

    }

    private fun checkForNotification() {
        val notiType = intent.getStringExtra(NotiInfo.NOTI_TYPE.type)

        when (notiType) {
            NotiType.CHATTING.type -> {
                Log.i(TAG,
                    "MessageType.CHATTING")
                val senderName = intent.getStringExtra(NotiInfo.SENDER_NAME.type)
                val senderUID  = intent.getStringExtra(NotiInfo.SENDER_UID.type)

                val intent = QuestionActivity.newIntent(this).apply {
                    putExtra(NotiInfo.SENDER_NAME.type, senderName)
                    putExtra(NotiInfo.SENDER_UID.type, senderUID)
                }
                startActivity(intent)
            }
            NotiType.PAY.type -> {
                Log.i(TAG,
                    "MessageType.PAY")
                val intent = PayCompleteActivity.newIntent(this).apply { }
                startActivity(intent)
            }
        }

//        if (extras != null) {
//            if (extras.containsKey(NotiInfo.NOTI_TYPE.type)   &&
//                extras.containsKey(NotiInfo.SENDER_UID.type)  &&
//                extras.containsKey(NotiInfo.SENDER_NAME.type) &&
//                extras.containsKey(NotiInfo.SENDER_TYPE.type)) {
//
//                val senderName = intent.getStringExtra(NotiInfo.SENDER_NAME.type)
//                val senderUID  = intent.getStringExtra(NotiInfo.SENDER_UID.type)
//
//                val intent = QuestionActivity.newIntent(this).apply {
//                    putExtra(NotiInfo.SENDER_NAME.type, senderName)
//                    putExtra(NotiInfo.SENDER_UID.type, senderUID)
//                    startActivity(this)
//                }
//            }
//
//        }
    }

    private fun setUpBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.home, R.navigation.message, R.navigation.mypage)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val controller = navView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment_activity_main,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        when (currentNavController?.value?.currentDestination?.label) {
            getString(R.string.title_home) -> backButtonCloseHandler.onBackPressed()
            else -> super.onBackPressed()

        }

    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}
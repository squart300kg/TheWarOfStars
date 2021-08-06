package com.the.war.of.thewarofstars

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
//import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.the.war.of.thewarofstars.databinding.ActivityMainBinding
import com.the.war.of.thewarofstars.ui.home.sub.sub.QuestionActivity
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
        if (intent?.extras != null) {

            val notiType   = intent.getStringExtra("notiType")
            val senderUID  = intent.getStringExtra("senderUID")
            val senderName = intent.getStringExtra("senderName")
            val senderType = intent.getStringExtra("senderType")
            val intent = QuestionActivity.newIntent(this).apply {
                putExtra("notiType", senderName)
                putExtra("uID", senderUID)
                putExtra("name", notiType)
                putExtra("senderType", senderType)
            }
            startActivity(intent)
        }
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
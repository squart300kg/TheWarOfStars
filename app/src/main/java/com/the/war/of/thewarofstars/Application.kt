package com.the.war.of.thewarofstars

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.nhn.android.naverlogin.OAuthLogin
import com.the.war.of.thewarofstars.di.networkModule
import com.the.war.of.thewarofstars.di.repositoryModule
import com.the.war.of.thewarofstars.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

open class Application: Application() {

    var firebaseDB: FirebaseFirestore? = null
    var naverLoginModule: OAuthLogin? = null

    var userUID: String? = null
    var userEmail: String? = null
    var userNickname: String? = null

    val TAG = "ApplicationLog"

    override fun onCreate() {
        super.onCreate()

        naverInitialize()

        koinInitialize()

        contextInitialize()

        firebaseInitialize()

        notificationInitialize()
    }

    private fun notificationInitialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHATTING", name, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun firebaseInitialize() {

        // Firestore 초기화
        firebaseDB = Firebase.firestore

        // FCM토큰 초기화
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d(TAG, "FCM Token : $token")
        })
    }

    private fun contextInitialize() {
        instance = this
    }

    private fun koinInitialize() = configureDi()

    private fun naverInitialize() {
        naverLoginModule = OAuthLogin.getInstance()
        naverLoginModule?.let {
            it.init(
                this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.app_name)
            )
        }
    }

    open fun configureDi() = startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@Application)
        modules(listOf(networkModule ,repositoryModule ,viewModelModule))
    }

    companion object {
        private const val Tag = "Sauce Application"
        var instance: com.the.war.of.thewarofstars.Application? = null

    }

}
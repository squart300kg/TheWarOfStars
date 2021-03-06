package com.the.war.of.thewarofstars

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.iamport.sdk.domain.core.Iamport
import com.nhn.android.naverlogin.OAuthLogin
import com.the.war.of.thewarofstars.di.networkModule
import com.the.war.of.thewarofstars.di.preferencesModule
import com.the.war.of.thewarofstars.di.repositoryModule
import com.the.war.of.thewarofstars.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * 4B:88:4F:EB:BA:9E:2E:9C:4E:A4:57:4E:BD:29:E3:78:36:89:6B:07
 */
open class Application: Application() {

    var firebaseStore: FirebaseFirestore? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var firebaseFunction: FirebaseFunctions? = null
    var naverLoginModule: OAuthLogin? = null

    var userUID: String?      = null
    var userEmail: String?    = null
    var userName: String?     = null
    var userFcmToken: String? = null
    var userType: String?     = null
    var userGameID: String? = null
    var userTribe: String? = null

    var isChatting = false

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
        createChannelFor(
            id = getString(R.string.notification_pay_channel_id),
            name = getString(R.string.notification_pay_channel_name)
        )
        createChannelFor(
            id = getString(R.string.notification_chatting_channel_id),
            name = getString(R.string.notification_chatting_channel_name)
        )
    }

    private fun createChannelFor(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

//            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            val notificationChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(notificationChannel)


        }
    }

    private fun firebaseInitialize() {

        // Firestore ?????????
        firebaseStore = Firebase.firestore

        // RDB?????????
        firebaseDatabase = Firebase.database
//        firebaseDatabase!!.useEmulator("172.32.4.12", 9000)

        // Function?????????
        firebaseFunction = Firebase.functions

        // FCM?????? ?????????
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            userFcmToken = task.result

            Log.d(TAG, "FCM Token : $userFcmToken")
        })
    }

    private fun contextInitialize() {
        instance = this
    }

    private fun koinInitialize() {

        val koinApp = startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(listOf(preferencesModule, networkModule ,repositoryModule ,viewModelModule))
        }

        Iamport.createWithKoin(this, koinApp)
    }

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

//    open fun configureDi() = startKoin {
//        androidLogger(Level.ERROR)
//        androidContext(this@Application)
//        modules(listOf(preferencesModule, networkModule ,repositoryModule ,viewModelModule))
//    }

    companion object {
        private const val Tag = "Sauce Application"
        var instance: com.the.war.of.thewarofstars.Application? = null

    }

}


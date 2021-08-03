package com.the.war.of.thewarofstars.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.the.war.of.thewarofstars.contant.MessageType
import com.the.war.of.thewarofstars.ui.home.sub.sub.QuestionActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMessagingServiceLog"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            when (remoteMessage.data["type"]) {
                MessageType.CHATTING.type -> {
                    Log.i(TAG, "noti start")
                    val intent = Intent(this, QuestionActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                    val notificationBuilder = NotificationCompat.Builder(this, "CHATTING")
                        .setTicker("setTicker")
                        .setContentTitle("helloContentTitle")
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                        .setContentText("helloContentText")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    val notificationManager = NotificationManagerCompat.from(this)
                    notificationManager.notify(0, notificationBuilder)
//                    startActivity(Intent(this, QuestionActivity::class.java))
                }
            }
        }


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Body: ${it.body}, Title: ${it.title}, icon: ${it.icon}")
        }
    }

}
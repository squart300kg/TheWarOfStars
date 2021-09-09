package com.the.war.of.thewarofstars.fcm

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.MessageType
import com.the.war.of.thewarofstars.ui.home.sub.pay.PayCompleteActivity
import com.the.war.of.thewarofstars.ui.home.sub.question.QuestionActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MessagingServiceLog"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            Log.d(TAG, "Message notification payload: ${remoteMessage.notification}")

            when (remoteMessage.data["notiType"]) {
                MessageType.CHATTING.type -> {
                    Log.i(TAG, "CHATTING type")

                    val intent = Intent(this, QuestionActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                    val contentTitle = remoteMessage.notification?.title
                    val contentBody  = remoteMessage.notification?.body

                    val notificationBuilder = NotificationCompat
                        .Builder(this, getString(R.string.notification_chatting_channel_id))
                        .setTicker(contentTitle)
                        .setContentTitle(contentTitle)
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                        .setContentText(contentBody)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    val notificationManager = NotificationManagerCompat.from(this)
                    notificationManager.notify(0, notificationBuilder)
                }
                MessageType.PAY.type -> {
                    Log.i(TAG, "PAY type")
                    val intent = Intent(this, PayCompleteActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                    val contentTitle = remoteMessage.notification?.title
                    val contentBody  = remoteMessage.notification?.body
                    val icon         = remoteMessage.notification?.icon

                    val notificationBuilder = NotificationCompat
                        .Builder(this, getString(R.string.notification_pay_channel_id))
                        .setTicker(contentTitle)
                        .setContentTitle(contentTitle)
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentText(contentBody)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    val notificationManager = NotificationManagerCompat.from(this)
                    notificationManager.notify(0, notificationBuilder)
                }
            }
        }


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Body: ${it.body}, Title: ${it.title}, icon: ${it.icon}")
        }
    }

}
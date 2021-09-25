package com.the.war.of.thewarofstars.fcm

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.NotiInfo
import com.the.war.of.thewarofstars.contant.NotiType
import com.the.war.of.thewarofstars.ui.home.sub.pay.PayCompleteActivity
import com.the.war.of.thewarofstars.ui.home.sub.pay.PayCompleteOkActivity
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

            when (remoteMessage.data[NotiInfo.NOTI_TYPE.type]) {
                NotiType.CHATTING.type -> {
                    if (isChatting() == false) {
                        Log.i(TAG, "CHATTING type")

                        val senderUID = remoteMessage.data[NotiInfo.SENDER_UID.type]
                        val senderName = remoteMessage.data[NotiInfo.SENDER_NAME.type]
                        val intent = Intent(this, QuestionActivity::class.java).apply {
                            putExtra(NotiInfo.SENDER_UID.type, senderUID)
                            putExtra(NotiInfo.SENDER_NAME.type, senderName)
                        }
                        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                        val contentTitle = remoteMessage.notification?.title
                        val contentBody  = remoteMessage.notification?.body

                        val notificationBuilder = NotificationCompat
                            .Builder(this, getString(R.string.notification_chatting_channel_id))
                            .setTicker(contentTitle)
                            .setContentTitle(contentTitle)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentText(contentBody)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build()

                        val notificationManager = NotificationManagerCompat.from(this)
                        notificationManager.notify(0, notificationBuilder)
                    }
                }
                NotiType.PAY.type -> {
                    Log.i(TAG, "PAY type")


                    val gamerUID = remoteMessage.data["gamerUID"]
                    val gamerName = remoteMessage.data["gamerName"]
                    val gamerCode = remoteMessage.data["gamerCode"]
                    val gamerTribe = remoteMessage.data["gamerTribe"]
                    val gamerID = remoteMessage.data["gamerID"]

                    val userUID = remoteMessage.data["userUID"]
                    val userNickname = remoteMessage.data["userNickname"]
                    val userCode = remoteMessage.data["userCode"]
                    val userTribe = remoteMessage.data["userTribe"]
                    val userID = remoteMessage.data["userID"]

                    val content = remoteMessage.data["content"]
                    val price = remoteMessage.data["price"]
                    val payDate = remoteMessage.data["payDate"]

                    val payUID = remoteMessage.data["payUID"]
                    val payStatus = remoteMessage.data["payStatus"]
                    val intent = Intent(this, PayCompleteActivity::class.java).apply {
                        putExtra("gamerUID", gamerUID)
                        putExtra("gamerName", gamerName)
                        putExtra("gamerCode", gamerCode)
                        putExtra("gamerTribe", gamerTribe)
                        putExtra("gamerID", gamerID)

                        putExtra("userUID", userUID)
                        putExtra("userNickname", userNickname)
                        putExtra("userCode", userCode)
                        putExtra("userTribe", userTribe)
                        putExtra("userID", userID)

                        putExtra("content", content)
                        putExtra("price", price)
                        putExtra("payDate", payDate)

                        putExtra("payUID", payUID)
                        putExtra("payStatus", payStatus)
                    }

                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                    val contentTitle = remoteMessage.notification?.title
                    val contentBody  = remoteMessage.notification?.body
                    val icon         = remoteMessage.notification?.icon

                    val notificationBuilder = NotificationCompat
                        .Builder(this, getString(R.string.notification_pay_channel_id))
                        .setTicker(contentTitle)
                        .setContentTitle(contentTitle)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentText(contentBody)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    val notificationManager = NotificationManagerCompat.from(this)
                    notificationManager.notify(0, notificationBuilder)
                }
                NotiType.PAY_SUCCESS.type -> {
                    val gamerUID = remoteMessage.data["gamerUID"]
                    val gamerName = remoteMessage.data["gamerName"]
                    val gamerCode = remoteMessage.data["gamerCode"]
                    val gamerTribe = remoteMessage.data["gamerTribe"]
                    val gamerID = remoteMessage.data["gamerID"]

                    val userUID = remoteMessage.data["userUID"]
                    val userNickname = remoteMessage.data["userNickname"]
                    val userCode = remoteMessage.data["userCode"]
                    val userTribe = remoteMessage.data["userTribe"]
                    val userID = remoteMessage.data["userID"]

                    val price = remoteMessage.data["price"]
                    val payDate = remoteMessage.data["payDate"]
                    val payStatus = remoteMessage.data["payStatus"]
                    val payUID = remoteMessage.data["payUID"]
                    val intent = Intent(this, PayCompleteOkActivity::class.java).apply {
                        putExtra("gamerUID", gamerUID)
                        putExtra("gamerName", gamerName)
                        putExtra("gamerCode", gamerCode)
                        putExtra("gamerTribe", gamerTribe)
                        putExtra("gamerID", gamerID)

                        putExtra("userUID", userUID)
                        putExtra("userNickname", userNickname)
                        putExtra("userCode", userCode)
                        putExtra("userTribe", userTribe)
                        putExtra("userID", userID)

                        putExtra("price", price)
                        putExtra("payDate", payDate)
                        putExtra("payStatus", payStatus)
                        putExtra("payUID", payUID)
                    }
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                    val contentTitle = remoteMessage.notification?.title
                    val contentBody  = remoteMessage.notification?.body
                    val icon         = remoteMessage.notification?.icon

                    val notificationBuilder = NotificationCompat
                        .Builder(this, getString(R.string.notification_pay_channel_id))
                        .setTicker(contentTitle)
                        .setContentTitle(contentTitle)
                        .setSmallIcon(R.drawable.ic_notification)
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

    private fun isChatting() = Application.instance?.isChatting

}
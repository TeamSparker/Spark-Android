package com.spark.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.spark.android.ui.intro.IntroActivity

class SparkMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val alarmId = remoteMessage.sentTime.toInt()
            val intent = Intent(this, IntroActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val builder =
                NotificationCompat.Builder(this, getString(R.string.app_name))
                    .setContentTitle(remoteMessage.data["title"].toString())
                    .setContentText(remoteMessage.data["body"].toString())
                    .setSmallIcon(R.mipmap.ic_app_logo)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = getString(R.string.app_name)
            val channelName = getString(R.string.app_name)
            val channelImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            notificationManager.createNotificationChannel(channel)

            notificationManager.notify(alarmId, builder.build())
        }
    }
}

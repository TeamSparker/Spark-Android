package com.spark.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.spark.android.ui.intro.IntroActivity
import com.spark.android.util.ImageUrlTransformer
import java.lang.IllegalArgumentException

class SparkMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            if (remoteMessage.data["imageUrl"].toString().isNotBlank()) {
                transformImageUrlToBitmap(remoteMessage)
            } else {
                showNotificationWithoutImage(remoteMessage)
            }
        }
    }

    private fun showNotificationWithoutImage(remoteMessage: RemoteMessage) {
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

    private fun showNotificationWithImage(remoteMessage: RemoteMessage, bitmap: Bitmap) {
        val alarmId = remoteMessage.sentTime.toInt()
        val intent = Intent(this, IntroActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, getString(R.string.app_name))
            .setContentTitle(remoteMessage.data["title"].toString())
            .setContentText(remoteMessage.data["body"].toString())
            .setSmallIcon(R.mipmap.ic_app_logo)
            .setLargeIcon(bitmap)
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

    private fun transformImageUrlToBitmap(remoteMessage: RemoteMessage) {
        val imageUrl = remoteMessage.data["imageUrl"].toString()
        Glide.with(this)
            .asBitmap()
            .load(ImageUrlTransformer.getSmallSizeImageUrl(imageUrl))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    showNotificationWithImage(remoteMessage, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}

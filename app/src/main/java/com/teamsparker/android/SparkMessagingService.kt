package com.teamsparker.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teamsparker.android.ui.intro.IntroActivity
import com.teamsparker.android.util.ImageCropUtil
import com.teamsparker.android.util.useBitmapImg
import timber.log.Timber
import java.lang.IllegalArgumentException

class SparkMessagingService : FirebaseMessagingService() {
    data class NotificationCategory(
        val summaryId: Int,
        val groupName: String
    )

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("fcm token").d(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            if (remoteMessage.data["imageUrl"].toString().isNotBlank()) {
                transformImageUrlToBitmap(remoteMessage)
            } else {
                createNotificationWithoutImage(remoteMessage)
            }
        }
    }

    private fun showAlarm(alarmId: Int, category: String, builder: NotificationCompat.Builder) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = getChannelId(category)
        val channelName = getString(R.string.app_name)
        val channelImportance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, channelImportance)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(alarmId, builder.build())
        notificationManager.notify(getSummaryId(category), getSummary(category).build())
    }

    private fun createNotificationWithoutImage(remoteMessage: RemoteMessage) {
        val alarmId = remoteMessage.sentTime.toInt()
        val category = remoteMessage.data["category"].toString()
        val intent = Intent(this, IntroActivity::class.java).apply {
            putExtra(OPEN_FROM_PUSH_ALARM, category)
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder =
            NotificationCompat.Builder(this, getChannelId(category))
                .setContentTitle(remoteMessage.data["title"].toString())
                .setContentText(remoteMessage.data["body"].toString())
                .setSmallIcon(R.mipmap.ic_app_logo)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setGroup(category)
        showAlarm(alarmId, category, builder)
    }

    private fun createNotificationWithImage(remoteMessage: RemoteMessage, bitmap: Bitmap) {
        val alarmId = remoteMessage.sentTime.toInt()
        val category = remoteMessage.data["category"].toString()
        val intent = Intent(this, IntroActivity::class.java).apply {
            putExtra(OPEN_FROM_PUSH_ALARM, category)
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, getChannelId(category))
            .setContentTitle(remoteMessage.data["title"].toString())
            .setContentText(remoteMessage.data["body"].toString())
            .setSmallIcon(R.mipmap.ic_app_logo)
            .setLargeIcon(bitmap)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setGroup(category)
        showAlarm(alarmId, category, builder)
    }

    private fun transformImageUrlToBitmap(remoteMessage: RemoteMessage) {
        val imageUrl = remoteMessage.data["imageUrl"].toString()
        useBitmapImg(this, imageUrl) { bitmap ->
            createNotificationWithImage(
                remoteMessage,
                if (bitmap.width != bitmap.height) {
                    ImageCropUtil.squareCropBitmap(bitmap)
                } else {
                    bitmap
                }
            )
        }
    }

    private fun getChannelId(category: String) =
        getSummaryId(category).toString() + getString(R.string.app_name)

    private fun getSummary(category: String): NotificationCompat.Builder {
        val intent = Intent(this, IntroActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(this, getChannelId(category))
            .setSmallIcon(R.mipmap.ic_app_logo)
            .setContentTitle(getString(R.string.app_name))
            .setContentIntent(pendingIntent)
            .setGroup(category)
            .setGroupSummary(true)
    }

    private fun getSummaryId(category: String) = when (category) {
        CATEGORY_CERTIFICATION.groupName -> CATEGORY_CERTIFICATION.summaryId
        CATEGORY_SPARK.groupName -> CATEGORY_SPARK.summaryId
        CATEGORY_REMIND.groupName -> CATEGORY_REMIND.summaryId
        CATEGORY_ROOM_START.groupName -> CATEGORY_ROOM_START.summaryId
        CATEGORY_CONSIDER.groupName -> CATEGORY_CONSIDER.summaryId
        else -> throw IllegalArgumentException("FCM category ?????? ??????")
    }

    companion object {
        const val OPEN_FROM_PUSH_ALARM = "openPushAlarm"
        private val CATEGORY_CERTIFICATION = NotificationCategory(0, "certification")
        private val CATEGORY_SPARK = NotificationCategory(1, "spark")
        private val CATEGORY_REMIND = NotificationCategory(2, "remind")
        private val CATEGORY_ROOM_START = NotificationCategory(3, "roomStart")
        private val CATEGORY_CONSIDER = NotificationCategory(4, "consider")
    }
}

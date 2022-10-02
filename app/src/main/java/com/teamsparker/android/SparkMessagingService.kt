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
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.util.ImageCropUtil
import com.teamsparker.android.util.NotificationCategory
import com.teamsparker.android.util.useBitmapImg
import timber.log.Timber
import java.lang.IllegalArgumentException

class SparkMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("fcm token").d(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            when (remoteMessage.data["category"].toString()) {
                NotificationCategory.CERTIFICATION.category ->
                    transformImageUrlToBitmap(remoteMessage)
                NotificationCategory.SPARK.category,
                NotificationCategory.REMIND.category,
                NotificationCategory.ROOM_START.category,
                NotificationCategory.CONSIDER.category ->
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
        val roomId = requireNotNull(remoteMessage.data["roomId"]).toInt()
        val intent = Intent(this, IntroActivity::class.java).apply {
            putExtra(OPEN_FROM_PUSH_ALARM, category)
            putExtra(ROOM_ID, roomId)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
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
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
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
        NotificationCategory.CERTIFICATION.category -> NotificationCategory.CERTIFICATION.summaryId
        NotificationCategory.SPARK.category -> NotificationCategory.SPARK.summaryId
        NotificationCategory.REMIND.category -> NotificationCategory.REMIND.summaryId
        NotificationCategory.ROOM_START.category -> NotificationCategory.ROOM_START.summaryId
        NotificationCategory.CONSIDER.category -> NotificationCategory.CONSIDER.summaryId
        else -> throw IllegalArgumentException("FCM category 필드 오류")
    }

    companion object {
        const val OPEN_FROM_PUSH_ALARM = "openPushAlarm"
        const val ROOM_ID = "roomId"
    }
}

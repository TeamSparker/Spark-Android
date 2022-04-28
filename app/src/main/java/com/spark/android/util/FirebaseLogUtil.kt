package com.spark.android.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FirebaseLogUtil {
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun logScreenEvent(screenClass: String, screenName: String) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }

    fun logClickEvent(contentName: String) {
        Firebase.analytics.logEvent(contentName, null)
    }

    fun logClickEventWithStartDate(contentName: String) {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        val formattedDate = date.format(formatter)
        Firebase.analytics.logEvent(contentName) {
            param(FirebaseAnalytics.Param.START_DATE, formattedDate)
        }
    }
}

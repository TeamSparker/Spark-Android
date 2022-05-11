package com.teamsparker.android.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FirebaseLogUtil {
    private const val DATE_FORMAT = "yyyy-MM-dd"

    const val SCREEN_SIGN_IN = "view_social_signup"
    const val SCREEN_HOME = "view_home"
    const val SCREEN_WAITING_ROOM = "view_waiting_room"
    const val SCREEN_HABIT_ROOM = "view_habit_room"
    const val SCREEN_STOPWATCH = "view_stopwatch"

    const val CLICK_FINISH_SIGN_UP = "click_FINISH_SIGNUP"
    const val CLICK_NEXT_CREATE_ROOM = "click_NEXT_create_room"
    const val CLICK_OK_INPUT_CODE = "click_OK_input_code"
    const val CLICK_START_HABIT = "click_START_HABIT"
    const val CLICK_CERTIFYING_NOW = "click_CERTIFYING_NOW"
    const val CLICK_UPLOAD = "click_UPLOAD"
    const val CLICK_FEED = "click_FEED"
    const val CLICK_SHARE_INSTAGRAM = "click_SHARE_INSTARGRAM"
    const val CLICK_INPUT_TEXT_SPARK = "click_INPUT_TEXT_spark"
    const val CLICK_MASSAGE_FIGHTING_SPARK = "click_MASSAGE_FIGHTING_spark"
    const val CLICK_MASSAGE_TOGETHER_SPARK = "click_MASSAGE_TOGETHER_spark"
    const val CLICK_MASSAGE_YOU_ONLY_SPARK = "click_MASSAGE_UONLY_spark"
    const val CLICK_MASSAGE_HURRY_SPARK = "click_MASSAGE_HURRY_spark"
    const val CLICK_CONSIDER_HABIT_ROOM = "click_CONSIDER_habit_room"
    const val CLICK_HEART_FEED = "click_HEART_feed"
    const val CLICK_CARD_MY_ROOM = "click_CARD_my_room"

    private const val NOTIFICATION_OPEN = "notification_open_"


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

    fun logNotificationOpenEvent(category: String) {
        Firebase.analytics.logEvent(NOTIFICATION_OPEN + category.uppercase(), null)
    }
}

package com.teamsparker.android.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber


object CheckForeground : Application.ActivityLifecycleCallbacks {
    private var instance: CheckForeground? = null

    var activityCount = 0

    fun init(app: Application) {
        if (instance == null) {
            instance = CheckForeground
            app.registerActivityLifecycleCallbacks(instance)
        }
    }

    fun isForeground(): Boolean = activityCount >= 1

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        activityCount++
        Timber.tag("push_alarm").d("${activityCount} activity_started")
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        activityCount--
        Timber.tag("push_alarm").d("${activityCount} activity_stopped-------------")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

}

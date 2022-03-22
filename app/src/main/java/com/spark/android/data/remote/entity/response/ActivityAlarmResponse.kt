package com.spark.android.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class ActivityAlarmResponse(
    val newService: Boolean,
    @SerializedName("notices")
    val alarms: List<ActivityAlarm>
)

data class ActivityAlarm(
    val day: String,
    val isNew: Boolean,
    val isThumbProfile: Boolean,
    val noticeContent: String,
    val noticeId: Int,
    val noticeImg: String,
    val noticeTitle: String
)

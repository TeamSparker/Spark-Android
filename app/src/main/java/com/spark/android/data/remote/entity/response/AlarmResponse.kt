package com.spark.android.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class AlarmResponse(
    val newService: Boolean,
    @SerializedName("notices")
    val alarms: List<Alarm>
)

data class Alarm(
    val day: String,
    val isNew: Boolean,
    val isThumbProfile: Boolean,
    val noticeContent: String,
    val noticeId: Int,
    val noticeImg: String?,
    val noticeTitle: String,
    var isFirst: Boolean = false
)

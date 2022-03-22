package com.spark.android.data.remote.entity.response

data class ActivityAlarmResponse(
    val newService: Boolean,
    val notices: List<Notice>
)

data class Notice(
    val day: String,
    val isNew: Boolean,
    val isThumbProfile: Boolean,
    val noticeContent: String,
    val noticeId: Int,
    val noticeImg: String,
    val noticeTitle: String
)

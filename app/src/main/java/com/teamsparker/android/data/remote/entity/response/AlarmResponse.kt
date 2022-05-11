package com.teamsparker.android.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class AlarmResponse(
    val newService: Boolean?,
    val newActive: Boolean?,
    @SerializedName("notices")
    val alarms: List<Alarm>
)

data class Alarm(
    val noticeId: Int,
    val noticeTitle: String,
    val noticeContent: String,
    val day: String,
    val isNew: Boolean,
    val noticeImg: String?,
    val isThumbProfile: Boolean?,
    var isFirst: Boolean = false
)

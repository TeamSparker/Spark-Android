package com.teamsparker.android.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class HabitRoomTimeLine(
    @SerializedName("timelines")
    val timelines: List<Timeline>
)

data class Timeline(
    @SerializedName("content")
    val content: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("isNew")
    val isNew: Boolean,
    @SerializedName("profiles")
    val profiles: List<String>,
    @SerializedName("title")
    val title: String
)

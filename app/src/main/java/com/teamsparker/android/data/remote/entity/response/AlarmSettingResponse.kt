package com.teamsparker.android.data.remote.entity.response

data class AlarmSettingResponse(
    val roomStart: Boolean,
    val spark: Boolean,
    val consider: Boolean,
    val certification: Boolean,
    val remind: Boolean
)

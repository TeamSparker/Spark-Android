package com.spark.android.data.remote.entity.response

data class HabitResponse(
    val endDate: String,
    val fromStart: Boolean,
    val leftDay: Int,
    val life: Int,
    val moment: String,
    val myRecord: HabitRecord,
    val otherRecords: List<OtherRecord>,
    val purpose: String,
    val roomId: Int,
    val roomName: String,
    val startDate: String,
)

data class OtherRecord(
    val nickname: String,
    val profileImg: String,
    val recordId: Int,
    val status: String,
    val userId: Int,
)

data class HabitRecord(
    val nickname: String,
    val profileImg: String,
    val receivedSpark: Int = -1,
    val recordId: Int,
    val rest: Int = -1,
    val status: String,
    val userId: Int,
)
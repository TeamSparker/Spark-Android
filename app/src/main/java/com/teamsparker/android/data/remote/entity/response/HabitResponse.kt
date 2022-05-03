package com.teamsparker.android.data.remote.entity.response

data class HabitResponse(
    val roomId: Int,
    val roomName: String,
    val leftDay: Int,
    val life: Int,
    val lifeDeductionCount : Int,
    val startDate: String,
    val endDate: String,
    val fromStart: Boolean,
    val moment: String,
    val purpose: String,
    val myRecord: HabitRecord,
    val otherRecords: List<OtherRecord>,
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

data class OtherRecord(
    val nickname: String,
    val profileImg: String,
    val recordId: Int,
    val status: String,
    val userId: Int,
)

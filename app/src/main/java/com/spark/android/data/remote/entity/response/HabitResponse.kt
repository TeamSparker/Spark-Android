package com.spark.android.data.remote.entity.response

data class HabitResponse(
    val endDate: String,
    val fromStart: Boolean,
    val leftDay: Int,
    val life: Int,
    val moment: String,
    val myRecord: MyRecord,
    val othersRecords: List<OthersRecord>,
    val purpose: String,
    val roomId: Int,
    val roomName: String,
    val startDate: String,
)

data class MyRecord(
    val nickname: String,
    val profileImg: String,
    val receivedSpark: Int,
    val recordId: Int,
    val rest: Int,
    val status: String,
    val userId: Int,
)

data class OthersRecord(
    val nickname: String,
    val profileImg: String,
    val recordId: Int,
    val status: String,
    val userId: Int,
)
package com.spark.android.ui.home.data

data class TicketData(
    val roomId : Int,
    val roomName : String,
    val leftDay :String?,
    val profileImg : List<String>?,
    val life : Int?,
    val isStarted : Boolean,
    val isDone : Boolean?,
    val memberNum : Int?,
    val doneMemberNum : Int?,
)

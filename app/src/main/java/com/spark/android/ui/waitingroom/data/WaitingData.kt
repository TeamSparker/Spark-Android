package com.spark.android.ui.waitingroom.data

data class WaitingData(
    val roomId : Int,
    val roomName : String,
    val roomCode : String,
    val fromStart : Boolean,
    val reqUser : ReqUser,
    val members : List<Members>
)   {
    data class ReqUser(
        val userId : Int,
        val nickName : String,
        val profileImg : String,
        val isPurposeSet : Boolean,
        val moment : String,
        val purpose : String,
        val isHost : Boolean
    )

    data class Members(
        val userId : Int,
        val nickName : String,
        val profileImg: String
    )
}

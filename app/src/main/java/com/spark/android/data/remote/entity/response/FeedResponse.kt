package com.spark.android.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("records")
    val feedList: List<Feed>
)

data class Feed(
    val certifyingImg: String?,
    val date: String,
    val day: String,
    var isLiked: Boolean,
    var likeNum: Int,
    val nickname: String,
    val profileImg: String?,
    val recordId: Int,
    val roomName: String,
    var sparkCount: Int,
    val timerRecord: String?,
    val userId: Int
)

data class FeedListItem(
    val id: String,
    val viewType: Int,
    val headerDate: String = "",
    val headerDay: String = "",
    val feed: Feed?
)

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
    val isLiked: Boolean,
    val likeNum: Int,
    val nickname: String,
    val profileImg: String?,
    val recordId: Int,
    val roomName: String,
    val sparkCount: Int,
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

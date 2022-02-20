package com.spark.android.data.remote.entity.response


data class PhotoCollectionResponse(
    val roomName: String,
    val records: List<StorageCardPhoto>
)

data class StorageCardPhoto(
    val certifyingImg: String,
    val leftDay: Int,
    val recordId: Int,
    val sparkNum: Int,
    val status: String,
    val timerRecord: String
)
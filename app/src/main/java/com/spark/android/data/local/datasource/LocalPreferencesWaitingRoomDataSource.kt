package com.spark.android.data.local.datasource

interface LocalPreferencesWaitingRoomDataSource {

    fun setHomeToastMessage(message:String)
    fun setHomeToastMessageState(state:Boolean)

}
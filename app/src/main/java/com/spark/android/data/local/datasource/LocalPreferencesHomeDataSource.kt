package com.spark.android.data.local.datasource

interface LocalPreferencesHomeDataSource {

    fun getHomeToastMessage():String
    fun getHomeToastMessageState():Boolean

}
package com.teamsparker.android.data.local.datasource

interface LocalPreferencesHomeDataSource {

    fun getHomeToastMessage():String
    fun getHomeToastMessageState():Boolean
    fun setHomeToastMessage(message:String)
    fun setHomeToastMessageState(state:Boolean)

}

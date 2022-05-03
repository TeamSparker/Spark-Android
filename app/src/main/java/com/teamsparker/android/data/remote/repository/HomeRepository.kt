package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.HomeResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.HomeNoticeRedDotResponese
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface HomeRepository {

    suspend fun getHomeAllRoom(lastId : Int, size : Int) : Result<BaseResponse<HomeResponse>>

    suspend fun readFinishHabitRoom(roomId: Int): Result<NoDataResponse>

    suspend fun getHomeNoticeRedDot(): Result<BaseResponse<HomeNoticeRedDotResponese>>

    fun getHomeToastMessage():String

    fun getHomeToastMessageState():Boolean

    fun setHomeToastMessage(message:String)

    fun setHomeToastMessageState(state:Boolean)

}

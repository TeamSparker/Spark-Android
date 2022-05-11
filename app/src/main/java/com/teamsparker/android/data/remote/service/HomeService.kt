package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.HomeResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("room")
    suspend fun getHomeAllRoom(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int
    ): BaseResponse<HomeResponse>

}

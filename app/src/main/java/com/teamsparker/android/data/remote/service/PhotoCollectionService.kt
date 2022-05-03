package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.PhotoCollectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoCollectionService {
    @GET("myroom/{roomId}")
    fun getPhotoCollectionData(
        @Path("roomId") roomId : Int,
        @Query("lastId") lastId: Int,
        @Query("size") size: Int
    ): Call<BaseResponse<PhotoCollectionResponse>>
}

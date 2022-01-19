package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.StorageResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Query

interface StorageService  {
    @GET("myroom")
    fun getStorageData(
        @Query("type") type:String,
        @Query("lastid") lastid:Int,
        @Query("size") size:Int
    ): Call<BaseResponse<StorageResponse>>
}
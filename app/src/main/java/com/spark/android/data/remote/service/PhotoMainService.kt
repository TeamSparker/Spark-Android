package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.NoDataResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PhotoMainService {
    @PATCH("myroom/{roomId}/thumbnail/{recordId}")
    fun patchPhotoMainData(
        @Path("roomId") roomId: Int,
        @Path("recordId") recordId : Int
    ): Call<NoDataResponse>
}

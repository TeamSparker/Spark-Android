package com.spark.android.ui.storage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.PhotoCollectionResponse
import com.spark.android.data.remote.entity.response.StorageCardPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoCollectionViewModel : ViewModel() {

    private val _photoCollectionResponse = MutableLiveData<PhotoCollectionResponse>()
    val photoCollectionResponse: LiveData<PhotoCollectionResponse> = _photoCollectionResponse

    private val _photoList = MutableLiveData<List<StorageCardPhoto>>()
    val photoList: LiveData<List<StorageCardPhoto>> = _photoList

    fun initPhotoCollectionNetwork(roomId: Int, lastid: Int, size: Int) {
        val call: Call<BaseResponse<PhotoCollectionResponse>> =
            RetrofitBuilder.photoCollectionService.getPhotoCollectionData(roomId, lastid, size)

        call.enqueue(object : Callback<BaseResponse<PhotoCollectionResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<PhotoCollectionResponse>>,
                response: Response<BaseResponse<PhotoCollectionResponse>>
            ) {
                if (response.isSuccessful) {
                    val photoCollectionData = response.body()?.data!!
                    _photoCollectionResponse.postValue(photoCollectionData)
                    _photoList.postValue(photoCollectionData.records)
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<PhotoCollectionResponse>>, t: Throwable
            ) {
                Log.e("NetworkTest", "error:$t")
            }

        })
    }
}
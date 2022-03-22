package com.spark.android.ui.storage.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.PhotoCollectionResponse
import com.spark.android.data.remote.entity.response.StorageCardPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _photoCollectionResponse = MutableLiveData<PhotoCollectionResponse>()
    val photoCollectionResponse: LiveData<PhotoCollectionResponse> = _photoCollectionResponse

    private val _photoList = MutableLiveData<List<StorageCardPhoto>>()
    val photoList: LiveData<List<StorageCardPhoto>> = _photoList

    private val _patchRoomId = MutableLiveData<Int>()
    val patchRoomId: LiveData<Int> = _patchRoomId

    private val _patchRecordId = MutableLiveData<Int>()
    val patchRecordId: LiveData<Int> = _patchRecordId

    fun setPatchRoomId(patchRoomId : Int){
        _patchRoomId.postValue(patchRoomId)
    }

    fun setPatchRecordId(patchRecordId : Int){
        _patchRecordId.postValue(patchRecordId)
    }

    private fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun isSelectable(position: Int): Boolean {
        return (_photoCollectionResponse.value?.records?.get(position)?.status == "DONE")
    }

    fun initPhotoCollectionNetwork(roomId: Int, lastId: Int, size: Int) {
        initIsLoading(true)
        val call: Call<BaseResponse<PhotoCollectionResponse>> =
            RetrofitBuilder.photoCollectionService.getPhotoCollectionData(roomId, lastId, size)

        call.enqueue(object : Callback<BaseResponse<PhotoCollectionResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<PhotoCollectionResponse>>,
                response: Response<BaseResponse<PhotoCollectionResponse>>
            ) {
                if (response.isSuccessful) {
                    val photoCollectionData = response.body()?.data!!
                    _photoCollectionResponse.postValue(photoCollectionData)
                    _photoList.postValue(photoCollectionData.records)
                    initIsLoading(false)
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<PhotoCollectionResponse>>, t: Throwable
            ) {
                Log.e("NetworkTest", "error:$t")
            }
        })
    }

    fun initPhotoMainNetwork() {
        initIsLoading(true)
        val call: Call<NoDataResponse> =
            RetrofitBuilder.photoMainService.patchPhotoMainData(patchRoomId.value?:0, patchRecordId.value?:0)
        call.enqueue(object : Callback<NoDataResponse> {

            override fun onResponse(
                call: Call<NoDataResponse>,
                response: Response<NoDataResponse>
            ) {
                if (response.isSuccessful) initIsLoading(false)
            }

            override fun onFailure(call: Call<NoDataResponse>, t: Throwable) {
            }
        })
    }
}


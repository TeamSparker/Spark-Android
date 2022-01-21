package com.spark.android.ui.storage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.StorageResponse
import com.spark.android.data.remote.entity.response.StorageRoom
import com.spark.android.ui.storage.StorageMode.Companion.COMPLETE
import com.spark.android.ui.storage.StorageMode.Companion.INCOMPLETE
import com.spark.android.ui.storage.StorageMode.Companion.PROGRESSING
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StorageViewModel : ViewModel() {

    private val _storageMode = MutableLiveData<String>()
    val storageMode: LiveData<String> = _storageMode

    private val _storageResponse = MutableLiveData<StorageResponse>()
    val storageResponse: LiveData<StorageResponse> = _storageResponse

    private val _progressingRooms = MutableLiveData<List<StorageRoom>>()
    val progressingRooms: LiveData<List<StorageRoom>> = _progressingRooms

    private val _incompleteRooms = MutableLiveData<List<StorageRoom>>()
    val incompleteRooms: LiveData<List<StorageRoom>> = _incompleteRooms

    private val _completeRooms = MutableLiveData<List<StorageRoom>>()
    val completeRooms: LiveData<List<StorageRoom>> = _completeRooms


    fun initProgressMode() {
        _storageMode.value = PROGRESSING
    }

    fun initCompleteMode() {
        _storageMode.value = COMPLETE
    }

    fun initIncompleteMode() {
        _storageMode.value = INCOMPLETE
    }

    fun initStorageNetwork(type: String, lastid: Int, size: Int) {
        val call: Call<BaseResponse<StorageResponse>> =
            RetrofitBuilder.storageService.getStorageData(type, lastid, size)

        call.enqueue(object : Callback<BaseResponse<StorageResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<StorageResponse>>,
                response: Response<BaseResponse<StorageResponse>>
            ) {
                if (response.isSuccessful) {
                    val storageData = response.body()?.data!!
                    _storageResponse.postValue(storageData)
                    when(type){
                        PROGRESSING -> _progressingRooms.postValue(storageData.rooms)
                        INCOMPLETE -> _incompleteRooms.postValue(storageData.rooms)
                        COMPLETE -> _completeRooms.postValue(storageData.rooms)
                    }
                }
            }
            override fun onFailure(call: Call<BaseResponse<StorageResponse>>, t: Throwable) {
                Log.e("NetworkTest", "error:$t")
            }
        })
    }
}
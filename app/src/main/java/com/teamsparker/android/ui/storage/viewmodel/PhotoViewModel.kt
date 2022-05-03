package com.teamsparker.android.ui.storage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamsparker.android.data.remote.RetrofitBuilder
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.PhotoCollectionResponse
import com.teamsparker.android.data.remote.entity.response.StorageCardPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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

    private fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setPatchRoomId(patchRoomId: Int) {
        _patchRoomId.postValue(patchRoomId)
    }

    fun setPatchRecordId(patchRecordId: Int) {
        _patchRecordId.postValue(patchRecordId)
    }

    fun initGetPhotoCollectionNetwork(roomId: Int, lastId: Int, size: Int) {
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
                Timber.tag("네트워크결과_GetPhotoCollection").e(t, "error")
            }
        })
    }

    fun initPatchPhotoMainNetwork() {
        initIsLoading(true)
        val call: Call<NoDataResponse> =
            RetrofitBuilder.photoMainService.patchPhotoMainData(
                patchRoomId.value ?: 0,
                patchRecordId.value ?: 0
            )
        call.enqueue(object : Callback<NoDataResponse> {

            override fun onResponse(
                call: Call<NoDataResponse>,
                response: Response<NoDataResponse>
            ) {
                if (response.isSuccessful) initIsLoading(false)
            }

            override fun onFailure(call: Call<NoDataResponse>, t: Throwable) {
                Timber.tag("네트워크결과_PatchPhotoMain").e(t, "error")
            }
        })
    }
}

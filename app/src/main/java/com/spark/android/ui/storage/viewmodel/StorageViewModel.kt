package com.spark.android.ui.storage.viewmodel

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
import timber.log.Timber

class StorageViewModel : ViewModel() {
    private var firstLoading = false

    var isInitProgressing = false
        private set
    var isInitComplete = false
        private set
    var isInitIncomplete = false
        private set

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

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


    private val _currentProgressingMode = MutableLiveData(false)
    val currentProgressingMode: LiveData<Boolean> = _currentProgressingMode

    private val _currentCompleteMode = MutableLiveData(false)
    val currentCompleteMode: LiveData<Boolean> = _currentCompleteMode

    private val _currentIncompleteMode = MutableLiveData(false)
    val currentIncompleteMode: LiveData<Boolean> = _currentIncompleteMode

    fun initVpInnerMode(cardType: String) {
        when (cardType) {
            "progressingCard" -> _currentProgressingMode.value = true
            "completeCard" -> _currentCompleteMode.value = true
            "incompleteCard" -> _currentIncompleteMode.value = true
        }
    }

    private fun initIsLoading(isLoading: Boolean) {
        if (!firstLoading) {
            _isLoading.value = isLoading
        }
    }

    fun initFirstLoading(isFirst: Boolean) {
        firstLoading = isFirst
    }

    fun initProgressMode() {
        _storageMode.value = PROGRESSING
    }

    fun initCompleteMode() {
        _storageMode.value = COMPLETE
    }

    fun initIncompleteMode() {
        _storageMode.value = INCOMPLETE
    }

    fun updateIsInitType(type: String) {
        when (type) {
            PROGRESSING -> isInitProgressing = true
            COMPLETE -> isInitComplete = true
            INCOMPLETE -> isInitIncomplete = true
            else -> throw IllegalStateException()
        }
    }

    fun initStorageNetwork(type: String, lastId: Int, size: Int) {
        initIsLoading(true)
        val call: Call<BaseResponse<StorageResponse>> =
            RetrofitBuilder.storageService.getStorageData(type, lastId, size)

        call.enqueue(object : Callback<BaseResponse<StorageResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<StorageResponse>>,
                response: Response<BaseResponse<StorageResponse>>
            ) {
                if (response.isSuccessful) {
                    val storageData = response.body()?.data!!
                    _storageResponse.postValue(storageData)
                    when (type) {
                        PROGRESSING -> _progressingRooms.postValue(storageData.rooms)
                        INCOMPLETE -> _incompleteRooms.postValue(storageData.rooms)
                        COMPLETE -> _completeRooms.postValue(storageData.rooms)
                    }
                    updateIsInitType(type)
                    initIsLoading(false)
                    initFirstLoading(true)
                }
            }

            override fun onFailure(call: Call<BaseResponse<StorageResponse>>, t: Throwable) {
                Timber.tag("네트워크결과_InitStorageNetwork").e(t, "error")
            }
        })
    }
}

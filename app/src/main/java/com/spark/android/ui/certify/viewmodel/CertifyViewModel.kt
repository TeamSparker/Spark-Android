package com.spark.android.ui.certify.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.RetrofitBuilder
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class CertifyViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _certifyMode = MutableLiveData<Int>()
    val certifyMode: LiveData<Int> = _certifyMode

    private val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String> = _roomName

    private val _roomId = MutableLiveData<Int>()
    val roomId: LiveData<Int> = _roomId

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _profileImg = MutableLiveData<String>()
    val profileImg: LiveData<String> = _profileImg

    private val _timerRecord = MutableLiveData<String?>()
    val timerRecord: LiveData<String?> = _timerRecord

    private val _onlyCameraInitial = MutableLiveData<Boolean>()
    val onlyCameraInitial: LiveData<Boolean> = _onlyCameraInitial

    private lateinit var certifyImgMultiPart: MultipartBody.Part

    private val _imgUri = MutableLiveData<Uri?>()
    val imgUri: LiveData<Uri?> = _imgUri

    private val _isSuccessCertify = MutableLiveData<Boolean>()
    val isSuccessCertify: LiveData<Boolean> = _isSuccessCertify

    fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun initOnlyCameraInitial(onlyCameraInitial: Boolean) {
        _onlyCameraInitial.value = onlyCameraInitial
    }

    fun initCertifyMode(certifyMode: Int) {
        _certifyMode.value = certifyMode
    }

    fun initRoomName(roomName: String) {
        _roomName.value = roomName
    }

    fun initRoomId(roomId: Int) {
        _roomId.value = roomId
    }

    fun initNickName(nickname: String) {
        _nickName.value = nickname
    }

    fun initProfileImg(profileImg: String) {
        _profileImg.value = profileImg
    }

    fun initTimerRecord(timerRecord: String?) {
        _timerRecord.value = timerRecord
    }

    fun initImgUri(uri: Uri) {
        _imgUri.value = uri
    }

    fun initCertifyImgMultiPart(multipart: MultipartBody.Part) {
        certifyImgMultiPart = multipart
    }

    fun postCertification() {
        initIsLoading(true)
        if (timerRecord.value.isNullOrEmpty()) {
            viewModelScope.launch {
                roomId.value?.let { roomId ->
                    kotlin.runCatching {
                        RetrofitBuilder.certifyService.postCertification(
                            roomId,
                            certifyImgMultiPart
                        )
                    }.onSuccess { response ->
                        _isSuccessCertify.postValue(response.success)
                        initIsLoading(false)
                    }.onFailure {

                    }
                }
            }
        } else {
            viewModelScope.launch {
                roomId.value?.let { roomId ->
                    kotlin.runCatching {
                        RetrofitBuilder.certifyService.postCertificationFromStart(
                            roomId,
                            mapOf("timerRecord" to requireNotNull(timerRecord.value).toRequestBody("text/plain".toMediaTypeOrNull())),
                            certifyImgMultiPart
                        )
                    }.onSuccess { response ->
                        _isSuccessCertify.postValue(response.success)
                        initIsLoading(false)
                    }.onFailure {

                    }
                }
            }
        }

    }
}

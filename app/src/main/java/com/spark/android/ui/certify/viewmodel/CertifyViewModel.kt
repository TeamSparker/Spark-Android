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
    private val _certifyMode = MutableLiveData<Int>()
    val certifyMode: LiveData<Int> = _certifyMode

    private val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String> = _roomName

    private val _roomId = MutableLiveData<Int>()
    val roomId: LiveData<Int> = _roomId

    private val _timerRecord = MutableLiveData<String>()
    val timerRecord: LiveData<String> = _timerRecord

    private lateinit var certifyImgMultiPart: MultipartBody.Part

    private val _imgUri = MutableLiveData<Uri?>()
    val imgUri: LiveData<Uri?> = _imgUri

    private val _imgBitmap = MutableLiveData<Bitmap?>()
    val imgBitmap: LiveData<Bitmap?> = _imgBitmap

    private val _isSuccessCertify = MutableLiveData<Boolean>()
    val isSuccessCertify: LiveData<Boolean> = _isSuccessCertify

    fun initCertifyMode(certifyMode: Int) {
        _certifyMode.value = certifyMode
    }

    fun initRoomName(roomName: String) {
        _roomName.value = roomName
    }

    fun initRoomId(roomId: Int) {
        _roomId.value = roomId
    }

    fun initTimerRecord(timerRecord: String) {
        _timerRecord.value = timerRecord
    }

    fun initImgUri(uri: Uri) {
        _imgUri.value = uri
        _imgBitmap.value = null
    }

    fun initImgBitmap(bitmap: Bitmap) {
        _imgBitmap.value = bitmap
        _imgUri.value = null
    }

    fun initCertifyImgMultiPart(multipart: MultipartBody.Part) {
        certifyImgMultiPart = multipart
    }

    fun postCertification() {
        val timerRecordMultiPart = if (timerRecord.value.isNullOrEmpty()) {
            null
        } else {
            mapOf("timerRecord" to requireNotNull(timerRecord.value).toRequestBody("text/plain".toMediaTypeOrNull()))
        }
        viewModelScope.launch {
            roomId.value?.let { roomId ->
                RetrofitBuilder.certifyService.postCertification(
                    roomId,
                    timerRecordMultiPart,
                    certifyImgMultiPart
                ).onSuccess { response ->
                    _isSuccessCertify.postValue(response.success)
                }.onFailure {

                }
            }
        }
    }
}
package com.spark.android.ui.certify.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody

class CertifyViewModel : ViewModel() {
    private val _certifyMode = MutableLiveData<Int>()
    val certifyMode: LiveData<Int> = _certifyMode

    private val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String> = _roomName

    private val _timerRecord = MutableLiveData<String>()
    val timerRecord: LiveData<String> = _timerRecord

    private lateinit var certifyImgMultiPart: MultipartBody.Part

    private val _imgUri = MutableLiveData<Uri?>()
    val imgUri: LiveData<Uri?> = _imgUri

    private val _imgBitmap = MutableLiveData<Bitmap?>()
    val imgBitmap: LiveData<Bitmap?> = _imgBitmap

    fun initCertifyMode(certifyMode: Int) {
        _certifyMode.value = certifyMode
    }

    fun initRoomName(roomName: String) {
        _roomName.value = roomName
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
}
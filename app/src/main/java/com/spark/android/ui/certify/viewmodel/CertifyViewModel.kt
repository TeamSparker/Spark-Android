package com.spark.android.ui.certify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CertifyViewModel : ViewModel() {
    private val _certifyMode = MutableLiveData<Int>()
    val certifyMode: LiveData<Int> = _certifyMode

    fun initCertifyMode(certifyMode: Int) {
        _certifyMode.value = certifyMode
    }
}
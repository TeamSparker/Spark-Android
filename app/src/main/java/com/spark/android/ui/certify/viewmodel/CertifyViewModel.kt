package com.spark.android.ui.certify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CertifyViewModel : ViewModel() {
    private val _onlyCamera = MutableLiveData<Boolean>()
    private val _isEditing = MutableLiveData<Boolean>()

    val onlyCamera: LiveData<Boolean> = _onlyCamera
    val isEditing: LiveData<Boolean> = _isEditing

    fun initOnlyCamera(onlyCamera:Boolean){
        _onlyCamera.value = onlyCamera
    }

    fun initIsEditing(isEditing:Boolean){
        _isEditing.value = isEditing
    }

}
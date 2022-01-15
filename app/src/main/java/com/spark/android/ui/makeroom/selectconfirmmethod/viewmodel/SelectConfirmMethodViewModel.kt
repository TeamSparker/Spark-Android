package com.spark.android.ui.makeroom.selectconfirmmethod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectConfirmMethodViewModel : ViewModel() {
    private val _methodState = MutableLiveData(METHOD_PICTURE)
    val methodState :LiveData<Boolean> = _methodState

    fun selectMethodPicture(){
        _methodState.value = METHOD_PICTURE
    }

    fun selectMethodTimer(){
        _methodState.value = METHOD_TIMER
    }

    companion object{
        const val METHOD_PICTURE = true
        const val METHOD_TIMER = false
    }
}
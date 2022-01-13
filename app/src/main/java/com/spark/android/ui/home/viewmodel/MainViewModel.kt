package com.spark.android.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _backgroundLayoutState = MutableLiveData<Boolean>()

    val backgroundLayoutState : LiveData<Boolean>
        get() = _backgroundLayoutState

}
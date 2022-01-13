package com.spark.android.ui.storage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StorageViewModel : ViewModel() {
    private val _sparkMode = MutableLiveData(0)
    val sparkMode: LiveData<Int> = _sparkMode

    fun initProgressMode() {
        _sparkMode.value = PROGRESSING
    }

    fun initCompleteMode() {
        _sparkMode.value = COMPLETE
    }

    fun initIncompleteMode() {
        _sparkMode.value = INCOMPLETE
    }

    companion object {
        private const val PROGRESSING = 0
        private const val COMPLETE = 1
        private const val INCOMPLETE = 2
    }
}
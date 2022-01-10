package com.spark.android.ui.storage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StorageViewModel : ViewModel() {
    private val _storagePage = MutableLiveData<Int>()
    val storagePage : LiveData<Int> = _storagePage

    companion object {
        private const val PROGRESSING = 0
        private const val COMPLETE = 1
        private const val INCOMPLETE = 2
    }
}
package com.spark.android.ui.joincode.inputcode.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.ui.feed.adapter.StickyHeaderResolver

class InputCodeFragmentDialogViewModel :ViewModel() {
    val roomCode = MutableLiveData<String>("")

    var _errorMessage = MutableLiveData<String>("")
    val errorMessage : LiveData<String> = _errorMessage

}
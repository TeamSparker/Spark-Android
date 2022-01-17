package com.spark.android.ui.setpurpose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetPurposeViewModel : ViewModel() {
    var habitWhen = MutableLiveData("")
    var myPurpose = MutableLiveData("")
}
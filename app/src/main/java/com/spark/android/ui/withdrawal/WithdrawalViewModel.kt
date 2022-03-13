package com.spark.android.ui.withdrawal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WithdrawalViewModel : ViewModel() {
    val isAgreeWithdrawal = MutableLiveData(false)
}

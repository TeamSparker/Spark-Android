package com.spark.android.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient

class ProfileViewModel : ViewModel() {
    val nickname = MutableLiveData("")
}

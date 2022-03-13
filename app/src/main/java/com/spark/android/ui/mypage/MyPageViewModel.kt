package com.spark.android.ui.mypage

import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) :ViewModel() {
}

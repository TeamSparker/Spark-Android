package com.spark.android.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.ProfileResponse
import com.spark.android.data.remote.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _profileData = MutableLiveData<ProfileResponse>()
    val profileData: LiveData<ProfileResponse> = _profileData

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile()
                .onSuccess { response ->
                    _profileData.postValue(requireNotNull(response.data))
                }.onFailure {
                    Log.d("MyPage_GetProfile", it.message.toString())
                }
        }
    }
}

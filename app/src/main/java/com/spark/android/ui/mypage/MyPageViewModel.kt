package com.spark.android.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.ProfileResponse
import com.spark.android.data.remote.repository.AlarmSettingRepository
import com.spark.android.data.remote.repository.AuthRepository
import com.spark.android.data.remote.repository.ProfileRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val alarmSettingRepository: AlarmSettingRepository
) : ViewModel() {
    private val _profileData = MutableLiveData<ProfileResponse>()
    val profileData: LiveData<ProfileResponse> = _profileData

    private val _isSuccessSignOut = MutableLiveData<Event<Boolean>>()
    val isSuccessSignOut: LiveData<Event<Boolean>> = _isSuccessSignOut

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile()
                .onSuccess { response ->
                    _profileData.postValue(requireNotNull(response.data))
                }.onFailure {
                    Timber.tag("MyPage_GetProfile").d(it.message.toString())
                }
        }
    }

    fun postSignOut() {
        viewModelScope.launch {
            authRepository.postSignOut()
                .onSuccess {
                    alarmSettingRepository.saveAlarmSettingLocalSaved(false)
                    authRepository.removeAccessToken()
                    authRepository.removeKakaoUserId()
                    _isSuccessSignOut.postValue(Event(true))
                }
                .onFailure {
                    Timber.tag("MyPage_SignOut").d(it.message.toString())
                }
        }
    }
}

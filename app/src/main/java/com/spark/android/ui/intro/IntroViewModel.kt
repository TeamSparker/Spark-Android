package com.spark.android.ui.intro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.BuildConfig
import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.local.datasource.LocalPreferencesDataSourceImpl.Companion.DEFAULT_STRING_VALUE
import com.spark.android.data.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val localPreferencesDataSource: LocalPreferencesDataSource,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _versionUpdateState = MutableLiveData<VersionUpdateState>()
    val versionUpdateState: LiveData<VersionUpdateState> = _versionUpdateState

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val isSuccessGetToken = MutableLiveData(false)

    private val isEndLottie = MutableLiveData(false)

    val isDone = MediatorLiveData<Boolean>().apply {
        addSource(isSuccessGetToken) { success ->
            value = success && (isEndLottie.value ?: false)
        }
        addSource(isEndLottie) { isEnd ->
            value = isEnd && (isSuccessGetToken.value ?: false)
        }
    }

    fun initIsEndLottie() {
        isEndLottie.value = true
    }

    fun hasToken() = localPreferencesDataSource.getAccessToken() != DEFAULT_STRING_VALUE

    fun initFcmToken() {
        authRepository.getFcmToken { token -> _fcmToken.value = token }
    }

    fun getFcmToken() {
        if (localPreferencesDataSource.getUserKakaoUserId() != DEFAULT_STRING_VALUE) {
            viewModelScope.launch {
                authRepository.getAccessToken(
                    socialId = localPreferencesDataSource.getUserKakaoUserId(),
                    fcmToken = requireNotNull(fcmToken.value)
                ).onSuccess { response ->
                    if (!response.data.isNew) {
                        localPreferencesDataSource.saveAccessToken(response.data.accesstoken)
                    }
                    isSuccessGetToken.postValue(true)
                }.onFailure {
                    Log.d("Intro_GetAccessToken", it.message.toString())
                    //getAccessToken()
                }
            }
        } else {
            isSuccessGetToken.postValue(true)
        }
    }

    fun versionCheck() {
        viewModelScope.launch {
            authRepository.getStoreVersion()
                .onSuccess { response ->
                    _versionUpdateState.postValue(
                        authRepository.versionCheck(response.data.version, BuildConfig.VERSION_NAME)
                    )
                }.onFailure {
                    Log.d("Intro_GetStoreVersion", it.message.toString())
                }
        }
    }
}

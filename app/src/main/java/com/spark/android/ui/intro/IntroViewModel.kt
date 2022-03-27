package com.spark.android.ui.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.local.datasource.LocalPreferencesDataSourceImpl.Companion.DEFAULT_LONG_VALUE
import com.spark.android.data.local.datasource.LocalPreferencesDataSourceImpl.Companion.DEFAULT_STRING_VALUE
import com.spark.android.data.remote.datasource.AuthDataSource
import com.spark.android.data.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val localPreferencesDataSource: LocalPreferencesDataSource,
    private val authRepository: AuthRepository
) : ViewModel() {
    private lateinit var fcmToken: String
    fun hasToken() = localPreferencesDataSource.getAccessToken() != DEFAULT_STRING_VALUE

    private fun initFcmToken(token: String) {
        fcmToken = token
    }

    fun getAccessToken() {
        if (localPreferencesDataSource.getUserKakaoUserId() != DEFAULT_LONG_VALUE) {
            authRepository.getFcmToken { token -> initFcmToken(token) }
            viewModelScope.launch {
                authRepository.getAccessToken(
                    socialId = localPreferencesDataSource.getUserKakaoUserId().toString(),
                    fcmToken = fcmToken
                ).onSuccess { response ->
                    localPreferencesDataSource.saveAccessToken(response.data.accesstoken)
                }.onFailure {
                    Log.d("Intro_GetAccessToken", it.message.toString())
                }
            }
        }
    }
}

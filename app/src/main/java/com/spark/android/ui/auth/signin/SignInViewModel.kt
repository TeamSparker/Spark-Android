package com.spark.android.ui.auth.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.spark.android.data.remote.entity.response.DoorbellResponse
import com.spark.android.data.remote.repository.AuthRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isSuccessKakaoLogin = MutableLiveData<Event<Boolean>>()
    val isSuccessKakaoLogin: LiveData<Event<Boolean>> = _isSuccessKakaoLogin

    private val _kakaoUserId = MutableLiveData<String>()
    val kakaoUserId: LiveData<String> = _kakaoUserId

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val _isInitUserInfo = MediatorLiveData<Boolean>()
    val isInitUserInfo: LiveData<Boolean> = _isInitUserInfo

    private val _doorbellResponse = MutableLiveData<DoorbellResponse>()
    val doorbellResponse: LiveData<DoorbellResponse> = _doorbellResponse

    fun addSourcesToIsInitUserInfo() {
        with(_isInitUserInfo) {
            this.addSource(_kakaoUserId) { id ->
                this.value = id.isNotBlank() && fcmToken.value != null
            }
            this.addSource(_fcmToken) { token ->
                this.value = token.isNotBlank() && kakaoUserId.value != null
            }
        }
    }

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            initIsSuccessLogin(false)
            Log.e("kakao", "로그인 실패", error)
        } else if (token != null) {
            initIsSuccessLogin(true)
            Log.d("kakao", "로그인 성공 ${token.accessToken}")
        }
    }

    fun initKakaoUserId() {
        authRepository.initKakaoUserId { id -> _kakaoUserId.value = id }
    }

    fun initFcmToken() {
        authRepository.getFcmToken { token -> _fcmToken.value = token }
    }

    fun saveAccessToken() {
        authRepository.saveAccessToken(requireNotNull(doorbellResponse.value).accesstoken)
    }

    private fun initIsSuccessLogin(isSuccess: Boolean) {
        _isSuccessKakaoLogin.value = Event(isSuccess)
    }

    fun getAccessToken() {
        viewModelScope.launch {
            authRepository.getAccessToken(
                requireNotNull(kakaoUserId.value),
                requireNotNull(fcmToken.value)
            ).onSuccess { response ->
                _doorbellResponse.postValue(requireNotNull(response.data))
            }.onFailure {
                Log.d("SignIn_getAccessToken", it.message.toString())
            }
        }

    }
}

package com.spark.android.ui.auth.signin

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
import timber.log.Timber
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

    private val _isInitUserInfo = MediatorLiveData<Event<Boolean>>().apply {
        addSource(_kakaoUserId) { id ->
            value = Event(id.isNotBlank() && fcmToken.value != null)
        }
        addSource(_fcmToken) { token ->
            value = Event(token.isNotBlank() && kakaoUserId.value != null)
        }
    }
    val isInitUserInfo: LiveData<Event<Boolean>> = _isInitUserInfo

    private val _doorbellResponse = MutableLiveData<Event<DoorbellResponse>>()
    val doorbellResponse: LiveData<Event<DoorbellResponse>> = _doorbellResponse

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            initIsSuccessLogin(false)
            Timber.tag("kakao").e(error, "로그인 실패")
        } else if (token != null) {
            initIsSuccessLogin(true)
            Timber.tag("kakao").d("로그인 성공 ${token.accessToken}")
        }
    }

    fun initKakaoUserId() {
        authRepository.initKakaoUserId { id -> _kakaoUserId.value = id }
    }

    fun initFcmToken() {
        authRepository.getFcmToken { token -> _fcmToken.value = token }
    }

    fun saveAccessToken() {
        authRepository.saveAccessToken(requireNotNull(doorbellResponse.value).peekContent().accesstoken)
    }

    private fun initIsSuccessLogin(isSuccess: Boolean) {
        _isSuccessKakaoLogin.value = Event(isSuccess)
    }

    fun resetDoorbellResponse() {
        _doorbellResponse.value = MutableLiveData<Event<DoorbellResponse>>().value
    }

    fun getAccessToken() {
        viewModelScope.launch {
            authRepository.getAccessToken(
                requireNotNull(kakaoUserId.value),
                requireNotNull(fcmToken.value)
            ).onSuccess { response ->
                _doorbellResponse.postValue(Event(requireNotNull(response.data)))
            }.onFailure {
                Timber.tag("SignIn_getAccessToken").d(it.message.toString())
            }
        }

    }
}

package com.spark.android.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.spark.android.data.repository.SignInRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: SignInRepository
) : ViewModel() {
    private val _isSuccessKakaoLogin = MutableLiveData<Event<Boolean>>()
    val isSuccessKakaoLogin: LiveData<Event<Boolean>> = _isSuccessKakaoLogin

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            initIsSuccessLogin(false)
            Log.e("kakao", "로그인 실패", error)
        } else if (token != null) {
            initIsSuccessLogin(true)
            Log.d("kakao", "로그인 성공 ${token.accessToken}")
        }
    }

    fun initKakaoLoginState() {
        signInRepository.startKakaoLogin(kakaoLoginCallback)
    }

    private fun initIsSuccessLogin(isSuccess: Boolean) {
        _isSuccessKakaoLogin.value = Event(isSuccess)
    }
}

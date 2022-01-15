package com.spark.android.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.spark.android.data.repository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: SignInRepository
) : ViewModel() {
    private val _kakaoLoginState = MutableLiveData<Int>()
    val kakaoLoginState: LiveData<Int> = _kakaoLoginState

    private val _kakaoUserId = MutableLiveData<Long>()
    val kakaoUserId: LiveData<Long> = _kakaoUserId


    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("kakao", "로그인 실패", error)
        } else if (token != null) {
            Log.d("kakao", "로그인 성공 ${token.accessToken}")
        }
    }

    fun initKakaoLoginState() {
        _kakaoLoginState.value = signInRepository.startKakaoLogin()
    }

    fun initKakaoUserId(userId: Long) {
        _kakaoUserId.value = userId
    }
}

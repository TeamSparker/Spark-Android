package com.spark.android.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient

class ProfileViewModel : ViewModel() {
    val nickname = MutableLiveData("")

    private val _kakaoUserId = MutableLiveData<Long>()
    val kakaoUserId: LiveData<Long> = _kakaoUserId

    private fun initKakaoUserId() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("kakao", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                _kakaoUserId.value = requireNotNull(tokenInfo.id)
                Log.d(
                    "kakao", "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초"
                )
            }
        }
    }
}

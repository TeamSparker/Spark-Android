package com.spark.android.data.repository

import android.util.Log
import com.kakao.sdk.user.UserApiClient
import kotlin.properties.Delegates

class ProfileRepositoryImpl : ProfileRepository {
    private var kakaoUserId by Delegates.notNull<Long>()

    private fun initKakaoUserId() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("kakao", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                kakaoUserId = requireNotNull(tokenInfo.id)
                Log.d(
                    "kakao", "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초"
                )
            }
        }
    }
}

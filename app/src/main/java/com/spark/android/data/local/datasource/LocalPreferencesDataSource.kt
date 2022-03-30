package com.spark.android.data.local.datasource

interface LocalPreferencesDataSource {
    fun saveAccessToken(accessToken: String)
    fun saveUserKakakoUserId(kakaoUserId: String)
    fun saveUserNickname(userNickname: String)
    fun getAccessToken(): String
    fun getUserKakaoUserId(): String
    fun getUserNickname(): String
    fun removeAccessToken()
    fun removeKakaoUserId()
}

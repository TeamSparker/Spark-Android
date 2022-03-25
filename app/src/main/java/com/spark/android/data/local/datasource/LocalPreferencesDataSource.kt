package com.spark.android.data.local.datasource

interface LocalPreferencesDataSource {
    fun saveAccessToken(accessToken: String)
    fun saveUserKakakoUserId(kakaoUserId: Long)
    fun saveUserNickname(userNickname: String)
    fun getAccessToken(): String
    fun getUserKakaoUserId(): Long
    fun getUserNickname(): String
    fun removeAccessToken()
    fun removeKakaoUserId()
}

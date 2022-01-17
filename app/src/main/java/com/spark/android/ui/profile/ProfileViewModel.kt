package com.spark.android.ui.profile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.AuthRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val nickname = MutableLiveData("")

    private val _nicknameFocused = MutableLiveData<Boolean>()
    val nicknameFocused: LiveData<Boolean> = _nicknameFocused

    private val _kakaoUserId = MutableLiveData<String>()
    val kakaoUserId: LiveData<String> = _kakaoUserId

    private val _successSignUp = MutableLiveData<Event<Boolean>>()
    val successSignUp: LiveData<Event<Boolean>> = _successSignUp

    private val _deleteMode = MutableLiveData(Event(false))
    val deleteMode: LiveData<Event<Boolean>> = _deleteMode

    private val _profileImgUri = MutableLiveData<Uri?>()
    val profileImgUri: LiveData<Uri?> = _profileImgUri

    private val _profileImgBitmap = MutableLiveData<Bitmap?>()
    val profileImgBitmap: LiveData<Bitmap?> = _profileImgBitmap

    fun initNicknameFocused(hasFocus: Boolean) {
        _nicknameFocused.value = !(requireNotNull(nickname.value).isEmpty() && !hasFocus)
    }

    private fun initDeleteMode(deleteMode: Boolean) {
        _deleteMode.value = Event(deleteMode)
    }

    fun initProfileImgUri(uri: Uri) {
        _profileImgUri.value = uri
        _profileImgBitmap.value = null
        initDeleteMode(true)
    }

    fun initProfileImgBitmap(bitmap: Bitmap) {
        _profileImgBitmap.value = bitmap
        _profileImgUri.value = null
        initDeleteMode(true)
    }

    fun deleteProfileImg() {
        _profileImgUri.value = null
        _profileImgBitmap.value = null
        initDeleteMode(false)
    }

    private fun initKakaoUserId(id: String) {
        _kakaoUserId.value = id
    }

    fun startSignUp() {
        authRepository.initKakaoUserId { id -> initKakaoUserId(id) }
    }

    fun postSignUp() {
        viewModelScope.launch {
            authRepository.postSignUp(
                requireNotNull(nickname.value),
                requireNotNull(kakaoUserId.value),
                null
            ).onSuccess { signUpResponse ->
                authRepository.saveAccessToken(signUpResponse.data.accessToken)
                _successSignUp.postValue(Event(true))
            }.onFailure {
                Log.d("Profile_SignUp", it.message.toString())
            }
        }
    }
}

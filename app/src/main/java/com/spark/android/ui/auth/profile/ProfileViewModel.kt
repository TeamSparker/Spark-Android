package com.spark.android.ui.auth.profile

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
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var profileImageMultiPart: MultipartBody.Part? = null

    val nickname = MutableLiveData("")

    private val _nicknameFocused = MutableLiveData<Boolean>()
    val nicknameFocused: LiveData<Boolean> = _nicknameFocused

    private var kakaoUserId: String = ""

    private var fcmToken: String = ""

    private val _successSignUp = MutableLiveData<Event<Boolean>>()
    val successSignUp: LiveData<Event<Boolean>> = _successSignUp

    private val _deleteMode = MutableLiveData(Event(false))
    val deleteMode: LiveData<Event<Boolean>> = _deleteMode

    private val _profileImgUri = MutableLiveData<Uri?>()
    val profileImgUri: LiveData<Uri?> = _profileImgUri

    private val _profileImgBitmap = MutableLiveData<Bitmap?>()
    val profileImgBitmap: LiveData<Bitmap?> = _profileImgBitmap

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun initProfileImgMultiPart(profileImg: MultipartBody.Part?) {
        profileImageMultiPart = profileImg
    }

    fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun initKakaoUserId() {
        authRepository.initKakaoUserId { id -> kakaoUserId = id }
    }

    fun initFcmToken() {
        authRepository.getFcmToken { token -> fcmToken = token }
    }

    fun postSignUp() {
        initIsLoading(true)
        viewModelScope.launch {
            authRepository.postSignUp(
                requireNotNull(nickname.value),
                kakaoUserId,
                fcmToken,
                profileImageMultiPart
            ).onSuccess { response ->
                authRepository.saveAccessToken(response.data.accessToken)
                _successSignUp.postValue(Event(true))
            }.onFailure {
                initIsLoading(false)
                Log.d("Profile_SignUp", it.message.toString())
            }
        }
    }
}

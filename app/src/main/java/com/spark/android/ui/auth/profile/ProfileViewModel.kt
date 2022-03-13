package com.spark.android.ui.auth.profile

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.AuthRepository
import com.spark.android.data.remote.repository.ProfileRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var profileImageMultiPart: MultipartBody.Part? = null

    val nickname = MutableLiveData("")

    private val _nicknameHintForModify = MutableLiveData<String>()
    val nicknameHintForModify: LiveData<String> = _nicknameHintForModify

    private val _nicknameFocused = MutableLiveData<Boolean>()
    val nicknameFocused: LiveData<Boolean> = _nicknameFocused

    private var kakaoUserId: String = ""

    private var fcmToken: String = ""

    private val _successSignUp = MutableLiveData<Event<Boolean>>()
    val successSignUp: LiveData<Event<Boolean>> = _successSignUp

    private val _deleteMode = MutableLiveData(Event(false))
    val deleteMode: LiveData<Event<Boolean>> = _deleteMode

    private val _profileImgUri = MutableLiveData(Uri.EMPTY)
    val profileImgUri: LiveData<Uri?> = _profileImgUri

    fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun initNicknameFocused(hasFocus: Boolean) {
        _nicknameFocused.value = !(requireNotNull(nickname.value).isEmpty() && !hasFocus)
    }

    private fun initDeleteMode(deleteMode: Boolean) {
        _deleteMode.value = Event(deleteMode)
    }

    fun initProfileImgUri(uri: Uri) {
        _profileImgUri.postValue(uri)
        initDeleteMode(true)
    }

    fun deleteProfileImg() {
        _profileImgUri.value = null
        initDeleteMode(false)
    }

    fun initProfileImgMultiPart(profileImg: MultipartBody.Part?) {
        profileImageMultiPart = profileImg
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

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile()
                .onSuccess { response ->
                    initProfileImgUri(response.data.profileImg.toUri())
                    nickname.postValue(response.data.nickname)
                    _nicknameHintForModify.postValue(response.data.nickname)
                    _nicknameFocused.postValue(true)
                }
                .onFailure {
                    Log.d("Profile_GetProfile", it.message.toString())
                }
        }
    }
}

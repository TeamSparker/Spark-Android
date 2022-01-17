package com.spark.android.ui.profile

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

    private val _kakaoUserId = MutableLiveData<String>()
    val kakaoUserId: LiveData<String> = _kakaoUserId

    private val _successSignUp = MutableLiveData<Event<Boolean>>()
    val successSignUp: LiveData<Event<Boolean>> = _successSignUp

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
            ).onSuccess {
                _successSignUp.postValue(Event(true))
            }.onFailure {
                Log.d("Profile_SignUp", it.message.toString())
            }
        }
    }
}

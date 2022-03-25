package com.spark.android.ui.withdrawal

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
class WithdrawalViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val isAgreeWithdrawal = MutableLiveData(false)

    private val _isSuccessWithdraw = MutableLiveData<Event<Boolean>>()
    val isSuccessWithdraw: LiveData<Event<Boolean>> = _isSuccessWithdraw

    fun deleteUser() {
        viewModelScope.launch {
            authRepository.deleteUser()
                .onSuccess {
                    authRepository.removeAccessToken()
                    authRepository.removeKakaoUserId()
                    unLinkKakaoAccount()
                }
                .onFailure {
                    Log.d("Withdrawal_deleteUser", it.message.toString())
                }
        }
    }

    fun unLinkKakaoAccount() {
        authRepository.unLinkKakaoAccount { isSuccess -> initIsSuccessWithdraw(isSuccess) }
    }

    private fun initIsSuccessWithdraw(isSuccess: Boolean) {
        _isSuccessWithdraw.postValue(Event(isSuccess))
    }
}

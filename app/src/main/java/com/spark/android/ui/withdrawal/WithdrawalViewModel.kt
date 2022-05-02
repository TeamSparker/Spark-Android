package com.spark.android.ui.withdrawal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.AlarmSettingRepository
import com.spark.android.data.remote.repository.AuthRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val alarmSettingRepository: AlarmSettingRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val isAgreeWithdrawal = MutableLiveData(false)

    private val _isSuccessWithdraw = MutableLiveData<Event<Boolean>>()
    val isSuccessWithdraw: LiveData<Event<Boolean>> = _isSuccessWithdraw

    fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun deleteUser() {
        initIsLoading(true)
        viewModelScope.launch {
            authRepository.deleteUser()
                .onSuccess {
                    alarmSettingRepository.saveAlarmSettingLocalSaved(false)
                    authRepository.removeAccessToken()
                    authRepository.removeKakaoUserId()
                    unLinkKakaoAccount()
                }
                .onFailure {
                    Timber.tag("Withdrawal_deleteUser").d(it.message.toString())
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

package com.spark.android.ui.setpurpose.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.repository.SetPurposeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPurposeViewModel @Inject constructor(
    private val setPurposeRepository: SetPurposeRepository
) : ViewModel() {

    var habitWhen = MutableLiveData("")
    var myPurpose = MutableLiveData("")
    var networkState = MutableLiveData(false)

    fun setPurpose(roomId: Int, body: SetPurposeRequest) {
        viewModelScope.launch {
            val response = setPurposeRepository.setPurpose(roomId,body)
            networkState.postValue(response.success)
        }
    }
}
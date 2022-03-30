package com.spark.android.ui.feedreport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.request.FeedReportRequest
import com.spark.android.data.remote.repository.FeedRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedReportViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    var feedItemId = -1
        private set
    val reportCause = MutableLiveData("")

    private val _causeFocused = MutableLiveData<Boolean>()
    val causeFocused: LiveData<Boolean> = _causeFocused

    private val _isSuccessReport = MutableLiveData<Event<Boolean>>()
    val isSuccessReport: LiveData<Event<Boolean>> = _isSuccessReport

    fun initFeedItemId(id: Int) {
        feedItemId = id
    }

    fun initCauseFocused(hasFocus: Boolean) {
        _causeFocused.value = !(requireNotNull(reportCause.value).isEmpty() && !hasFocus)
    }

    fun postFeedReport() {
        viewModelScope.launch {
            feedRepository.postFeedReport(
                feedItemId,
                FeedReportRequest(requireNotNull(reportCause.value))
            ).onSuccess {
                _isSuccessReport.postValue(Event(true))
            }.onFailure {
                Log.d("FeedReport_PostFeedReport", it.message.toString())
            }
        }
    }
}
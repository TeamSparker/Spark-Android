package com.spark.android.ui.feedreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedReportViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    val reportCause = MutableLiveData("")

    private val _causeFocused = MutableLiveData<Boolean>()
    val causeFocused: LiveData<Boolean> = _causeFocused

    fun initCauseFocused(hasFocus: Boolean) {
        _causeFocused.value = !(requireNotNull(reportCause.value).isEmpty() && !hasFocus)
    }
}

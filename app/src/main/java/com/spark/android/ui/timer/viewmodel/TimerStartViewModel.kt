package com.spark.android.ui.timer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerStartViewModel : ViewModel() {

    private val _timerState = MutableLiveData(TIMER_RESET)
    val timerState: LiveData<Int> get() = _timerState

    fun setState(state: Int) {
        _timerState.value = state
    }

    companion object {
        const val TIMER_RESET = 0
        const val TIMER_RUN = 1
        const val TIMER_PAUSE = 2
    }
}
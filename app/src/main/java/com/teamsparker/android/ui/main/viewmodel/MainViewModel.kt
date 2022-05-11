package com.teamsparker.android.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _tabPosition = MutableLiveData<Int>()
    val tabPosition: LiveData<Int> = _tabPosition

    fun initTabPositionFeed() {
        _tabPosition.value = TAB_FEED
    }

    fun initTabPositionHome() {
        _tabPosition.value = TAB_HOME
    }

    fun initTabPositionStorage() {
        _tabPosition.value = TAB_STORAGE
    }

    companion object {
        const val TAB_FEED = 0
        const val TAB_HOME = 1
        const val TAB_STORAGE = 2
    }
}

package com.spark.android.ui.intro

import androidx.lifecycle.ViewModel
import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val localPreferencesDataSource: LocalPreferencesDataSource
) : ViewModel() {
    fun hasToken() = localPreferencesDataSource.getAccessToken() != ""
}

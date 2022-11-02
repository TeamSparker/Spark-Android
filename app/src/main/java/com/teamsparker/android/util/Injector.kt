package com.teamsparker.android.util

import android.content.SharedPreferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

sealed interface Injector {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface SharedPreferencesInjector {
        fun sharedPreferences(): SharedPreferences
    }
}

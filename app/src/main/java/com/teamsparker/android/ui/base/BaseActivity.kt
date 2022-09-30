package com.teamsparker.android.ui.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.teamsparker.android.data.local.datasource.LocalPreferencesDataSourceImpl
import com.teamsparker.android.data.local.datasource.LocalPreferencesDataSourceImpl.Companion.ACCESS_TOKEN
import com.teamsparker.android.data.local.datasource.LocalPreferencesDataSourceImpl.Companion.ALARM_LOCAL_SAVED
import com.teamsparker.android.data.local.datasource.LocalPreferencesDataSourceImpl.Companion.USER_KAKAO_USER_ID
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_CERTIFICATION
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_CONSIDER
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_REMIND
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_ROOM_START
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_SPARK
import com.teamsparker.android.ui.auth.AuthActivity
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.Injector
import dagger.hilt.android.EntryPointAccessors
import com.teamsparker.android.ui.alarmsetting.AlarmSettingViewModel.Companion as AlarmSettingViewModel1

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : AppCompatActivity() {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
    }

    private val sharedPreferences: SharedPreferences by lazy {
        EntryPointAccessors.fromActivity(
            this,
            Injector.SharedPreferencesInjector::class.java
        ).sharedPreferences()
    }

    protected fun terminationTokenHandling(viewModel: BaseViewModel) {
        viewModel.moveToLogin.observe(
            this,
            EventObserver {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                sharedPreferences.edit { remove(ACCESS_TOKEN) }
                sharedPreferences.edit { remove(USER_KAKAO_USER_ID) }
                sharedPreferences.edit { remove(ALARM_LOCAL_SAVED) }
                sharedPreferences.edit { remove(ALARM_ROOM_START) }
                sharedPreferences.edit { remove(ALARM_SPARK) }
                sharedPreferences.edit { remove(ALARM_CONSIDER) }
                sharedPreferences.edit { remove(ALARM_CERTIFICATION) }
                sharedPreferences.edit { remove(ALARM_REMIND) }
                finishAffinity()
            }
        )
    }
}

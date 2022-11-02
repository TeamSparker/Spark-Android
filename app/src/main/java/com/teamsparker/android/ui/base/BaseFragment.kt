package com.teamsparker.android.ui.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {
    private var _binding: T? = null
    protected val binding: T
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private val sharedPreferences: SharedPreferences by lazy {
        EntryPointAccessors.fromActivity(
            requireActivity(),
            Injector.SharedPreferencesInjector::class.java
        ).sharedPreferences()
    }

    fun terminationTokenHandling(viewModel: BaseViewModel) {
        viewModel.moveToLogin.observe(
            viewLifecycleOwner,
            EventObserver {
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
                sharedPreferences.edit { remove(ACCESS_TOKEN) }
                sharedPreferences.edit { remove(USER_KAKAO_USER_ID) }
                sharedPreferences.edit { remove(ALARM_LOCAL_SAVED) }
                sharedPreferences.edit { remove(ALARM_ROOM_START) }
                sharedPreferences.edit { remove(ALARM_SPARK) }
                sharedPreferences.edit { remove(ALARM_CONSIDER) }
                sharedPreferences.edit { remove(ALARM_CERTIFICATION) }
                sharedPreferences.edit { remove(ALARM_REMIND) }
                requireActivity().finishAffinity()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

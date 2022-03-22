package com.spark.android.ui.alarmcenter.servicealarm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spark.android.R
import com.spark.android.databinding.FragmentServiceAlarmBinding
import com.spark.android.ui.alarmcenter.acitivityalarm.adapter.AlarmPagingAdapter
import com.spark.android.ui.alarmcenter.servicealarm.viewmodel.ServiceAlarmViewModel
import com.spark.android.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ServiceAlarmFragment :
    BaseFragment<FragmentServiceAlarmBinding>(R.layout.fragment_service_alarm) {
    private val serviceAlarmViewModel by viewModels<ServiceAlarmViewModel>()
    private val serviceAlarmAdapter = AlarmPagingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvServiceAlarmAdapter()
        collectServiceAlarmList()
    }

    private fun initRvServiceAlarmAdapter() {
        binding.rvServiceAlarm.adapter = serviceAlarmAdapter
    }

    private fun collectServiceAlarmList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                serviceAlarmViewModel.getServiceAlarmPagingSource().collectLatest { alarmList ->
                    serviceAlarmAdapter.submitData(alarmList)
                }
            }
            serviceAlarmViewModel.patchServiceAlarm()
        }
    }
}

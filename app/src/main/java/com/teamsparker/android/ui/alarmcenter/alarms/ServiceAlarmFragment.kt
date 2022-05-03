package com.teamsparker.android.ui.alarmcenter.alarms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentServiceAlarmBinding
import com.teamsparker.android.ui.alarmcenter.AlarmCenterActivity
import com.teamsparker.android.ui.alarmcenter.alarms.adapter.AlarmPagingAdapter
import com.teamsparker.android.ui.alarmcenter.alarms.viewmodel.ServiceAlarmViewModel
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.util.EventObserver
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
        binding.serviceAlarmViewModel = serviceAlarmViewModel
        initRvServiceAlarmAdapter()
        collectServiceAlarmList()
        initEmptyView()
        initNewActivityObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.rvServiceAlarm.smoothScrollToPosition(0)
    }

    private fun initRvServiceAlarmAdapter() {
        binding.rvServiceAlarm.adapter = serviceAlarmAdapter
    }

    private fun collectServiceAlarmList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                serviceAlarmViewModel.getServiceAlarmPagingSource().collectLatest { alarmList ->
                    serviceAlarmAdapter.submitData(alarmList)
                }

            }
        }
    }

    private fun initEmptyView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                serviceAlarmAdapter.loadStateFlow.collectLatest { loadState ->
                    serviceAlarmViewModel.initEmptyServiceAlarm(
                        // loadState.refresh is LoadState.NotLoading && serviceAlarmAdapter.itemCount == 0
                        serviceAlarmAdapter.itemCount == 0
                    )
                }
            }
        }
    }

    private fun initNewActivityObserver() {
        serviceAlarmViewModel.newActivity.observe(viewLifecycleOwner, EventObserver { newActivity ->
            (activity as AlarmCenterActivity).initNewActivityAlarmSticker(newActivity)
        })
    }

    override fun onPause() {
        super.onPause()
        serviceAlarmViewModel.patchServiceAlarm()
    }
}

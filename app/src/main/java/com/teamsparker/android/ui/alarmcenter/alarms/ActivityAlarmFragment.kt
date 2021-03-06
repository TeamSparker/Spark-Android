package com.teamsparker.android.ui.alarmcenter.alarms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentActivityAlarmBinding
import com.teamsparker.android.ui.alarmcenter.AlarmCenterActivity
import com.teamsparker.android.ui.alarmcenter.alarms.adapter.AlarmPagingAdapter
import com.teamsparker.android.ui.alarmcenter.alarms.viewmodel.ActivityAlarmViewModel
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityAlarmFragment :
    BaseFragment<FragmentActivityAlarmBinding>(R.layout.fragment_activity_alarm) {
    private val activityAlarmViewModel by viewModels<ActivityAlarmViewModel>()
    private val activityAlarmAdapter = AlarmPagingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.activityAlarmViewModel = activityAlarmViewModel
        initRvActivityAlarmAdapter()
        collectActivityAlarmList()
        initEmptyView()
        initNewServiceObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.rvActivityAlarm.smoothScrollToPosition(0)
    }

    private fun initRvActivityAlarmAdapter() {
        binding.rvActivityAlarm.adapter = activityAlarmAdapter
    }

    private fun collectActivityAlarmList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                activityAlarmViewModel.getActivityAlarmPagingSource().collectLatest { alarmList ->
                    activityAlarmAdapter.submitData(alarmList)
                }
            }
        }
    }

    private fun initEmptyView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityAlarmAdapter.loadStateFlow.collectLatest { loadState ->
                    activityAlarmViewModel.initEmptyActivityAlarm(
                        // loadState.refresh is LoadState.NotLoading && activityAlarmAdapter.itemCount == 0
                        activityAlarmAdapter.itemCount == 0
                    )
                }
            }
        }
    }

    private fun initNewServiceObserver() {
        activityAlarmViewModel.newService.observe(viewLifecycleOwner, EventObserver { newService ->
            (activity as AlarmCenterActivity).initNewServiceAlarmSticker(newService)
        })
    }

    override fun onPause() {
        super.onPause()
        activityAlarmViewModel.patchActivityAlarm()
    }
}

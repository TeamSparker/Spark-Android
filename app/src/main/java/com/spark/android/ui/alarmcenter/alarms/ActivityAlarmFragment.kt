package com.spark.android.ui.alarmcenter.alarms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spark.android.R
import com.spark.android.databinding.FragmentActivityAlarmBinding
import com.spark.android.ui.alarmcenter.AlarmCenterActivity
import com.spark.android.ui.alarmcenter.alarms.adapter.AlarmPagingAdapter
import com.spark.android.ui.alarmcenter.alarms.viewmodel.ActivityAlarmViewModel
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.EventObserver
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
        initRvActivityAlarmAdapter()
        collectActivityAlarmList()
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

package com.spark.android.ui.habit

import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.data.remote.LocalPreferences
import com.spark.android.databinding.ActivityHabitBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitRecyclerViewAdapter
import com.spark.android.ui.habit.userguide.UserGuideFragmentDialog
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.util.initStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HabitActivity : BaseActivity<ActivityHabitBinding>(R.layout.activity_habit) {
    private lateinit var habitRecyclerViewAdapter: HabitRecyclerViewAdapter

    private val habitViewModel by viewModels<HabitViewModel>()
    private var roomId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.habitViewModel = habitViewModel

        initStatusBarColor(R.color.spark_black)
        initRoomId()
        initRVAdapter()
        initHabitInfoObserver()
        initHabitRecordsObserver()
        setSwipeRefreshLayout()
        initHabitBackBtnClickListener()
        initHabitMoreBtnClickListener()
        initHabitTodayBtnClickListener()
        setRefreshDataFragmentResultListener()
        setExitHabitRoomFragmentResultListener()
        checkUserGuideDialog()
        initHabitLifeLessDialog()
    }

    private fun initRoomId() {
        roomId = intent.getIntExtra("roomId", -1)
    }

    private fun initHabitInfoObserver() {
        habitViewModel.habitInfo.observe(this) {
            habitRecyclerViewAdapter.response = it
            binding.habitViewModel = habitViewModel
        }
    }

    private fun initHabitRecordsObserver() {
        habitViewModel.habitRecordList.observe(this) {
            habitRecyclerViewAdapter.updateHabitList(it)
        }
    }

    private fun refreshData() {
        if (roomId != -1) {
            habitViewModel.getHabitRoomInfo(roomId)
        }
    }

    private fun setSwipeRefreshLayout() {
        with(binding.swipeHabitRefresh) {
            setColorSchemeColors(context.getColor(R.color.spark_pinkred))
            setOnRefreshListener {
                refreshData()
                isRefreshing = false
            }
        }
    }

    private fun initRVAdapter() {
        habitRecyclerViewAdapter = HabitRecyclerViewAdapter()
        binding.rvHabitTeamList.adapter = habitRecyclerViewAdapter
    }

    private fun initHabitBackBtnClickListener() {
        binding.btnHabitBack.setOnClickListener {
            finish()
        }
    }

    private fun initHabitMoreBtnClickListener() {
        binding.btnHabitMore.setOnClickListener {
            HabitMoreBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun initHabitTodayBtnClickListener() {
        binding.btnHabitTodayCertification.setOnClickListener {
            HabitTodayBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun setRefreshDataFragmentResultListener() {
        supportFragmentManager
            .setFragmentResultListener("refreshHabitData", this) { requestKey, bundle ->
                refreshData()
            }
    }

    private fun setExitHabitRoomFragmentResultListener() {
        supportFragmentManager
            .setFragmentResultListener("exitHabitRoom", this) { requestKey, bundle ->
                LocalPreferences.setExitHabitRoomHomeToastMessage("‘${habitViewModel.habitInfo.value!!.roomName}’ 방을 나갔어요.")
                LocalPreferences.setExitHabitRoomHomeToastMessageState(true)
                finish()
            }
    }

    private fun checkUserGuideDialog(){
        if(habitViewModel.getUserGuideDialogState()){
            UserGuideFragmentDialog().show(
                supportFragmentManager, "UserGuideDialog"
            )
            habitViewModel.setUserGuideDialogState(false)
        }
    }

    private fun initHabitLifeLessDialog(){

            HabitLifeLessDialogFragment().show(
                supportFragmentManager, "LifeLessDialog"
            )

    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}

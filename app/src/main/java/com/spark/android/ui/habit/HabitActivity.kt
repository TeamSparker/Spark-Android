package com.spark.android.ui.habit

import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitRecyclerViewAdapter
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.util.initStatusBarColor
import kotlin.properties.Delegates

class HabitActivity : BaseActivity<ActivityHabitBinding>(R.layout.activity_habit) {
    private lateinit var habitRecyclerViewAdapter: HabitRecyclerViewAdapter

    private val habitViewModel by viewModels<HabitViewModel>()
    private var roomId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.habitViewModel = habitViewModel

        // test
        roomId = 160

        initStatusBarColor(R.color.spark_black)
//        initRoomId()
        initRVAdapter()
        initHabitInfoObserver()
        initHabitRecordsObserver()
        refreshData()
        setSwipeRefreshLayout()
        initHabitBackBtnClickListener()
        initHabitMoreBtnClickListener()
        initHabitTodayBtnClickListener()
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

    private fun initHabitRecordsObserver(){
        habitViewModel.habitRecordList.observe(this){
            habitRecyclerViewAdapter.list.addAll(it)
            habitRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    private fun refreshData() {
        habitRecyclerViewAdapter.list.clear()
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
            HabitTodayBottomSheet(1).show(supportFragmentManager, this.javaClass.name)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}

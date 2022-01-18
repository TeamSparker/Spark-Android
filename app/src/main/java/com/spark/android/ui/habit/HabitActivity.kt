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

        roomId = intent.getIntExtra("roomId", -1)
        roomId = 2

//        refreshData()

        habitViewModel.habitInfo.observe(this) {
            binding.habitViewModel = habitViewModel
        }

        initStatusBarColor(R.color.spark_black)
        setSwipeRefreshLayout()
        initRVAdapter()
        initHabitBackBtnClickListener()
        initHabitMoreBtnClickListener()
        initHabitTodayBtnClickListener()
    }

    private fun refreshData() {
        if(roomId != -1) {
            habitViewModel.getHabitRoomInfo(roomId)
        }
    }

    private fun setSwipeRefreshLayout() {
        with(binding.swipeHabitRefresh) {
            setColorSchemeColors(context.getColor(R.color.spark_pinkred))
            setOnRefreshListener {
                refreshData()
                // 함수 추가
                isRefreshing = false
            }
        }
    }

    private fun initRVAdapter() {
        // test
        var list = mutableListOf<String>()
        list.add("a")
        list.add("b")
        list.add("c")
        list.add("d")
        list.add("e")
        list.add("f")
        habitRecyclerViewAdapter = HabitRecyclerViewAdapter(list)
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

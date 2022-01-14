package com.spark.android.ui.habit

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitRecyclerViewAdapter
import com.spark.android.util.initStatusBarColor

class HabitActivity : BaseActivity<ActivityHabitBinding>(R.layout.activity_habit) {
    private lateinit var habitRecyclerViewAdapter: HabitRecyclerViewAdapter
    private lateinit var moreBottomSheetDialog: BottomSheetDialog
    private lateinit var todayBottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBarColor(R.color.spark_black)
        setSwipeRefreshLayout()
        initRVAdapter()
        initHabitMoreDialog()
        initHabitTodayDialog()
        initHabitBackBtnClickListener()
        initHabitMoreBtnClickListener()
        initHabitTodayBtnClickListener()
        initHabitMoreManageBtnClickListener()
    }


    private fun setSwipeRefreshLayout() {
        with(binding.swipeHabitRefresh) {
            setColorSchemeColors(context.getColor(R.color.spark_pinkred))
            setOnRefreshListener {
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

    private fun initHabitMoreDialog() {
        val moreBottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_habit_more, null)
        moreBottomSheetDialog = BottomSheetDialog(this, R.style.NewDialog)
//            val moreBottomSheetDialog = BottomSheetDialog(this)
        moreBottomSheetDialog.setContentView(moreBottomSheetView)
    }

    private fun initHabitTodayDialog() {
        val todayBottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_habit_today, null)
        todayBottomSheetDialog = BottomSheetDialog(this, R.style.NewDialog)
        todayBottomSheetDialog.setContentView(todayBottomSheetView)
    }

    private fun initHabitBackBtnClickListener() {
        binding.btnHabitBack.setOnClickListener {
            finish()
        }
    }

    private fun initHabitMoreBtnClickListener() {
        binding.btnHabitMore.setOnClickListener {
            moreBottomSheetDialog.show()
        }
    }

    private fun initHabitTodayBtnClickListener() {
        binding.btnHabitTodayCertification.setOnClickListener {
            todayBottomSheetDialog.findViewById<ImageView>(R.id.iv_habit_today_stopwatch)?.visibility =
                View.GONE
            todayBottomSheetDialog.show()
        }
    }

    private fun initHabitMoreManageBtnClickListener() {
        moreBottomSheetDialog.findViewById<TextView>(R.id.tv_habit_more_manage)
            ?.setOnClickListener {
                Toast.makeText(this, "manage", Toast.LENGTH_SHORT).show()
            }
    }
}

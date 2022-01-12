package com.spark.android.ui.habit

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitRecyclerViewAdapter
import com.spark.android.util.initStatusBarColor

class HabitActivity : BaseActivity<ActivityHabitBinding>(R.layout.activity_habit) {
    private lateinit var habitRecyclerViewAdapter: HabitRecyclerViewAdapter
    var testFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBarColor(R.color.spark_black)
        initRVAdapter()
        setSwipeRefreshLayout()
        initHabitBackBtnClickListener()
//        initHabitMoreBtnClickListener()
        initCertificationBtnClickListener()
    }

    private fun initHabitBackBtnClickListener() {
        binding.btnHabitBack.setOnClickListener {
            finish()
        }
    }

//    private fun initHabitMoreBtnClickListener() {
//        binding.btnHabitMore.setOnClickListener {
//            val moreBottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_habit_more, null)
////            val moreBottomSheetDialog = BottomSheetDialog(this, R.style.NewDialog)
//            val moreBottomSheetDialog = BottomSheetDialog(this)
//            moreBottomSheetDialog.setContentView(moreBottomSheetView)
//            moreBottomSheetDialog.show()
//        }
//    }

    private fun initCertificationBtnClickListener() {
        binding.btnHabitTodayCertification.setOnClickListener {
            if (testFlag) {
                binding.btnHabitTodayCertification.setBackgroundResource(R.drawable.bg_habit_today_inactive)
                binding.ivHabitTodayCertificationBg.visibility = View.GONE
                testFlag = !testFlag
            } else {
                binding.btnHabitTodayCertification.setBackgroundResource(R.drawable.bg_habit_today_active)
                binding.ivHabitTodayCertificationBg.visibility = View.VISIBLE
                testFlag = !testFlag
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

    private fun setSwipeRefreshLayout() {
        with(binding.swipeHabitRefresh) {
            setColorSchemeColors(context.getColor(R.color.spark_pinkred))
            setOnRefreshListener {
                // 함수 추가
                isRefreshing = false
            }
        }
    }
}

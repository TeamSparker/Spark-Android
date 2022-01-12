package com.spark.android.ui.habit

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitRecyclerViewAdapter

class HabitActivity : BaseActivity<ActivityHabitBinding>(R.layout.activity_habit) {
    private lateinit var habitRecyclerViewAdapter : HabitRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRVAdapter()
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
}
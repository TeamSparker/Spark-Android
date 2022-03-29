package com.spark.android.ui.habit

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitSendSparkBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitSendSparkRecyclerViewAdapter

class HabitSendSparkActivity :
    BaseActivity<ActivityHabitSendSparkBinding>(R.layout.activity_habit_send_spark) {
    var selectedItemId = -1
    var selectedItemNickname = ""
    var selectedItemProfileImg = ""
    var sendSparkMode = NORMAL_MODE
    private lateinit var habitSendSparkRecyclerViewAdapter: HabitSendSparkRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSelectedItem()
        initDataBinding()
        initLeftBtnClickListener()
        initRightBtnClickListener()
        initRVAdapter()

        // test
        binding.tvHabitSendSparkTitle.setOnClickListener {
            if (sendSparkMode == NORMAL_MODE) {
                sendSparkMode = MESSAGE_MODE
            } else if (sendSparkMode == MESSAGE_MODE) {
                sendSparkMode = NORMAL_MODE
            }
            binding.nickname = selectedItemNickname
            binding.profileImg = selectedItemProfileImg
            binding.mode = sendSparkMode
        }
    }

    private fun setSelectedItem() {
        selectedItemId = intent.getIntExtra("roomId", -1)
        selectedItemNickname = intent.getStringExtra("nickname").toString()
        selectedItemProfileImg = intent.getStringExtra("profileImg").toString()
    }

    private fun initDataBinding() {
        binding.nickname = selectedItemNickname
        binding.profileImg = selectedItemProfileImg
        binding.mode = sendSparkMode
    }

    private fun initLeftBtnClickListener() {
        binding.btnHabitSendSparkLeft.setOnClickListener {
            if (sendSparkMode == NORMAL_MODE) {
                finish()
            } else if (sendSparkMode == MESSAGE_MODE) {
                sendSparkMode = NORMAL_MODE
            }
        }
    }

    private fun initRightBtnClickListener() {
        binding.btnHabitSendSparkRight.setOnClickListener {
            finish()
        }
    }

    private fun initRVAdapter() {
        habitSendSparkRecyclerViewAdapter = HabitSendSparkRecyclerViewAdapter()
        binding.rvHabitSendSparkList.adapter = habitSendSparkRecyclerViewAdapter
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        if (sendSparkMode == NORMAL_MODE) {
            finish()
        } else if (sendSparkMode == MESSAGE_MODE) {
            sendSparkMode = NORMAL_MODE
        }
    }

    companion object {
        val NORMAL_MODE = 0
        val MESSAGE_MODE = 1
    }
}
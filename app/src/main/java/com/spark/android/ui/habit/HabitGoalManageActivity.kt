package com.spark.android.ui.habit

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitGoalManageBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite

class HabitGoalManageActivity :
    BaseActivity<ActivityHabitGoalManageBinding>(R.layout.activity_habit_goal_manage) {

    private val habitGoalManageViewModel by viewModels<HabitGoalManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.habitGoalManageViewModel = habitGoalManageViewModel

        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()

        initEditTextClearFocus()
        initTimeEditTextFocusListener()
        initGoalEditTextFocusListener()
        initQuitBtnClickListener()
    }

    private fun initEditTextClearFocus() {
        binding.layoutHabitGoalManage.setOnClickListener {
            binding.etHabitGoalTime.clearFocus()
            binding.etHabitGoalGoal.clearFocus()
        }
    }

    private fun initTimeEditTextFocusListener() {
        binding.etHabitGoalTime.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.tvHabitGoalManageTipTop.visibility = View.GONE
                binding.tvHabitGoalManageTipBottom.visibility = View.GONE
            } else {
                binding.tvHabitGoalManageTipTop.visibility = View.VISIBLE
                binding.tvHabitGoalManageTipBottom.visibility = View.VISIBLE
            }
        }
    }

    private fun initGoalEditTextFocusListener() {
        binding.etHabitGoalGoal.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.tvHabitGoalManageTipTop.visibility = View.GONE
                binding.tvHabitGoalManageTipBottom.visibility = View.GONE
            } else {
                binding.tvHabitGoalManageTipTop.visibility = View.VISIBLE
                binding.tvHabitGoalManageTipBottom.visibility = View.VISIBLE
            }
        }
    }

    private fun initQuitBtnClickListener() {
        binding.btnHabitGoalManageQuit.setOnClickListener {
            finish()
        }
    }
}
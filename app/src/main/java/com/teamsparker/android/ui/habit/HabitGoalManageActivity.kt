package com.teamsparker.android.ui.habit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityHabitGoalManageBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.habit.viewmodel.HabitGoalManageViewModel
import com.teamsparker.android.util.*

class HabitGoalManageActivity :
    BaseActivity<ActivityHabitGoalManageBinding>(R.layout.activity_habit_goal_manage) {

    private val habitGoalManageViewModel by viewModels<HabitGoalManageViewModel>()
    private var layoutState = false

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.habitGoalManageViewModel = habitGoalManageViewModel

        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()

        initIntentData()
        initEditTextClearFocus()
        initTimeEditTextTouchListener()
        initTimeEditTextFocusListener()
        initGoalEditTextTouchListener()
        initGoalEditTextFocusListener()
        initQuitBtnClickListener()
        initCompleteBtnClickListener()
        initKeyBoardEvent()
    }

    override fun onResume() {
        super.onResume()
        initUnderBarColor()
    }

    private fun initUnderBarColor() {
        if(!binding.etHabitGoalTime.text.isEmpty()){
            binding.viewHabitGoalTimeUnderBar.setBackgroundColor(
                ContextCompat.getColor(
                    binding.viewHabitGoalTimeUnderBar.context,
                    R.color.spark_pinkred
                )
            )
        }

        if(!binding.etHabitGoalGoal.text.isEmpty()){
            binding.viewHabitGoalGoalUnderBar.setBackgroundColor(
                ContextCompat.getColor(
                    binding.viewHabitGoalGoalUnderBar.context,
                    R.color.spark_pinkred
                )
            )
        }
    }

    private fun initIntentData() {
        habitGoalManageViewModel.initRoomId(intent.getIntExtra("roomId", -1))
        intent.getStringExtra("roomName")?.let { habitGoalManageViewModel.initRoomName(it) }
        habitGoalManageViewModel.moment.value = intent.getStringExtra("moment")
        habitGoalManageViewModel.purpose.value = intent.getStringExtra("purpose")
    }

    private fun initEditTextClearFocus() {
        binding.layoutHabitGoalManage.setOnClickListener {
            layoutState = false
            KeyBoardUtil.hide(this)
        }
    }

    private fun initTimeEditTextTouchListener() {
        binding.etHabitGoalTime.setOnClickListener {
            if (!layoutState) {
                AnimationUtil.getFocusInSetPurpose(
                    binding.tvHabitGoalManageTipTop,
                    binding.tvHabitGoalManageTipBottom,
                    binding.etHabitGoalTime,
                    binding.layoutHabitGoalManageMoving,
                    this,
                    binding.layoutHabitGoalManage
                )
                layoutState = true
            } else {
                binding.etHabitGoalTime.isFocusableInTouchMode = true
                binding.etHabitGoalTime.requestFocus()
                binding.etHabitGoalTime.isCursorVisible = true
                KeyBoardUtil.show(this)
            }
        }
    }

    private fun initTimeEditTextFocusListener() {
        binding.etHabitGoalTime.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewHabitGoalTimeUnderBar.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.viewHabitGoalTimeUnderBar.context,
                        R.color.spark_pinkred
                    )
                )
            } else {
                if(binding.etHabitGoalTime.text.isEmpty()){
                    binding.viewHabitGoalTimeUnderBar.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.viewHabitGoalTimeUnderBar.context,
                            R.color.spark_gray
                        )
                    )
                }
                binding.etHabitGoalTime.isCursorVisible = false
                binding.etHabitGoalTime.isFocusableInTouchMode = false
            }
        }
    }

    private fun initGoalEditTextTouchListener() {
        binding.etHabitGoalGoal.setOnClickListener {
            if (!layoutState) {
                AnimationUtil.getFocusInSetPurpose(
                    binding.tvHabitGoalManageTipTop,
                    binding.tvHabitGoalManageTipBottom,
                    binding.etHabitGoalGoal,
                    binding.layoutHabitGoalManageMoving,
                    this,
                    binding.layoutHabitGoalManage
                )
                layoutState = true
            } else {
                binding.etHabitGoalGoal.isFocusableInTouchMode = true
                binding.etHabitGoalGoal.requestFocus()
                binding.etHabitGoalGoal.isCursorVisible = true
                KeyBoardUtil.show(this)
            }
        }
    }

    private fun initGoalEditTextFocusListener() {
        binding.etHabitGoalGoal.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewHabitGoalGoalUnderBar.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.viewHabitGoalGoalUnderBar.context,
                        R.color.spark_pinkred
                    )
                )
            } else {
                if(binding.etHabitGoalGoal.text.isEmpty()){
                    binding.viewHabitGoalGoalUnderBar.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.viewHabitGoalGoalUnderBar.context,
                            R.color.spark_gray
                        )
                    )
                }
                binding.etHabitGoalGoal.isCursorVisible = false
                binding.etHabitGoalGoal.isFocusableInTouchMode = false
            }
        }
    }

    private fun initKeyBoardEvent() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(this.window,
            onHideKeyboard = {
                AnimationUtil.lostFocusInSetPurpose(
                    binding.tvHabitGoalManageTipTop,
                    binding.tvHabitGoalManageTipBottom,
                    binding.etHabitGoalTime,
                    binding.etHabitGoalGoal,
                    binding.layoutHabitGoalManageMoving
                )
                layoutState = false
                this.currentFocus?.clearFocus()
            }
        )
    }

    private fun initQuitBtnClickListener() {
        binding.btnHabitGoalManageQuit.setOnClickListener {
            finish()
        }
    }

    private fun initCompleteBtnClickListener() {
        binding.btnHabitGoalComplete.setOnClickListener {
            habitGoalManageViewModel.setPurpose()
            finish()
        }
    }
}

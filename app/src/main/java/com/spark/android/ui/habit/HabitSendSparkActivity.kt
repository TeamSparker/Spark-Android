package com.spark.android.ui.habit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.spark.android.R
import com.spark.android.databinding.ActivityHabitSendSparkBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.adapter.HabitSendSparkRecyclerViewAdapter
import com.spark.android.ui.habit.viewmodel.HabitSendSparkViewModel
import com.spark.android.util.KeyBoardUtil
import com.spark.android.util.KeyboardVisibilityUtils

class HabitSendSparkActivity :
    BaseActivity<ActivityHabitSendSparkBinding>(R.layout.activity_habit_send_spark) {
    private val habitSendSparkRecyclerViewAdapter =
        HabitSendSparkRecyclerViewAdapter(
            { content ->
                habitSendSparkViewModel.postSendSpark(content)
                finish()
            },
            { isTyping -> habitSendSparkViewModel.initIsTyping(isTyping) })
    private val habitSendSparkViewModel by viewModels<HabitSendSparkViewModel>()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.habitSendSparkViewModel = habitSendSparkViewModel

        initSelectedItem()
        initRVAdapter()
        initLeftBtnClickListener()
        initRightBtnClickListener()
        initSendSparkBtnClickListener()
        initSendEditTextTextChangedListener()
        initSendEditTextTextFocusListener()
        initKeyBoardEvent()
    }

    private fun initSelectedItem() {
        habitSendSparkViewModel.initRoomId(intent.getIntExtra("roomId", -1))
        habitSendSparkViewModel.initRecordId(intent.getIntExtra("recordId", -1))
        habitSendSparkViewModel.initNickname(intent.getStringExtra("nickname").toString())
        habitSendSparkViewModel.initProfileImg(intent.getStringExtra("profileImg").toString())
    }

    private fun initRVAdapter() {
        binding.rvHabitSendSparkList.adapter = habitSendSparkRecyclerViewAdapter
    }

    private fun initLeftBtnClickListener() {
        binding.btnHabitSendSparkLeft.setOnClickListener {
            if (habitSendSparkViewModel.isTyping.value == true) {
                KeyBoardUtil.hide(this)
                binding.etSendSparkMessage.clearFocus()
                habitSendSparkViewModel.initIsTyping(false)
            } else {
                finish()
            }
        }
    }

    private fun initRightBtnClickListener() {
        binding.btnHabitSendSparkRight.setOnClickListener {
            finish()
        }
    }

    private fun initSendSparkBtnClickListener() {
        binding.btnHabitSendSparkSend.setOnClickListener {
            habitSendSparkViewModel.postSendSpark(binding.etSendSparkMessage.text.toString())
            finish()
        }
    }

    private fun initSendEditTextTextChangedListener() {
        binding.etSendSparkMessage.addTextChangedListener {
            if (binding.etSendSparkMessage.text.isEmpty()) {
                binding.btnHabitSendSparkSend.setTextColor(
                    ContextCompat.getColor(
                        binding.btnHabitSendSparkSend.context,
                        R.color.spark_gray
                    )
                )
            } else {
                binding.btnHabitSendSparkSend.setTextColor(
                    ContextCompat.getColor(
                        binding.btnHabitSendSparkSend.context,
                        R.color.spark_pinkred
                    )
                )
            }
        }
    }

    private fun initSendEditTextTextFocusListener() {
        binding.etSendSparkMessage.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                KeyBoardUtil.show(this)
            }
        }
    }

    private fun initKeyBoardEvent() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(this.window,
            onHideKeyboard = {
                habitSendSparkViewModel.initIsTyping(false)
            }
        )
    }

    override fun onBackPressed() {
        if (habitSendSparkViewModel.isTyping.value == true) {
            habitSendSparkViewModel.initIsTyping(false)
        } else {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}

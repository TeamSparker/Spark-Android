package com.teamsparker.android.ui.habit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityHabitSendSparkBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.habit.adapter.HabitSendSparkRecyclerViewAdapter
import com.teamsparker.android.ui.habit.viewmodel.HabitSendSparkViewModel
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_INPUT_TEXT_SPARK
import com.teamsparker.android.util.KeyBoardUtil
import com.teamsparker.android.util.KeyboardVisibilityUtils
import com.teamsparker.android.util.SendSparkToast

class HabitSendSparkActivity :
    BaseActivity<ActivityHabitSendSparkBinding>(R.layout.activity_habit_send_spark) {
    private val habitSendSparkRecyclerViewAdapter =
        HabitSendSparkRecyclerViewAdapter(
            { content ->
                habitSendSparkViewModel.postSendSpark(content)
                SendSparkToast.showToast(this, habitSendSparkViewModel.nickname.value.toString())
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
        initBackgroundLayoutClickListener()
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
                onBackPressed()
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
            FirebaseLogUtil.logClickEvent(CLICK_INPUT_TEXT_SPARK)
            habitSendSparkViewModel.postSendSpark(binding.etSendSparkMessage.text.toString())
            SendSparkToast.showToast(this, habitSendSparkViewModel.nickname.value.toString())
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

    private fun initBackgroundLayoutClickListener() {
        binding.layoutHabitSendSpark.setOnClickListener {
            if (habitSendSparkViewModel.isTyping.value == true) {
                onBackPressed()
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
            KeyBoardUtil.hide(this)
        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        SendSparkToast.cancelToast()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}

package com.spark.android.ui.joincode

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.data.remote.entity.response.JoinCodeRoomInfoResponse
import com.spark.android.databinding.ActivityJoinCodeBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.joincode.viewmodel.JoinCodeViewModel
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.waitingroom.WaitingRoomActivity
import com.spark.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_JOIN_CODE
import com.spark.android.util.EventObserver
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinCodeActivity : BaseActivity<ActivityJoinCodeBinding>(R.layout.activity_join_code) {

    private val joinCodeViewModel by viewModels<JoinCodeViewModel>()
    private lateinit var roomInfo: JoinCodeRoomInfoResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtra()
        binding.joinCodeRoomInfoResponse = roomInfo
        initStatusBarStyle()
        initInputCodeAgainButtonListener()
        initEnterWaitingRoomButtonListener()
        initBackButtonListener()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveToMain()
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    private fun initExtra() {
        roomInfo = intent.getParcelableExtra("roomInfo")!!
    }

    private fun initInputCodeAgainButtonListener() {
        binding.btnJoinCodeInputAgain.setOnClickListener {
            val resultIntent = Intent(this,MainActivity::class.java).apply {
                putExtra("finishState", BACK_TO_JOIN_CODE)
            }
            setResult(RESULT_OK,resultIntent)
            finish()
        }
    }

    private fun initBackButtonListener() {
        binding.btnJoinCodeBack.setOnClickListener {
            moveToMain()
        }
    }

    private fun initEnterWaitingRoomButtonListener() {
        binding.btnJoinCodeEnterWaitingRoom.setOnClickListener {
            joinCodeViewModel.setJoinCodeRoomDone(roomInfo.roomId)
            val intent = Intent(this, WaitingRoomActivity::class.java).apply {
                putExtra("roomId", roomInfo.roomId)
                putExtra("startPoint", START_FROM_JOIN_CODE)
            }
            val resultIntent = Intent(this,MainActivity::class.java).apply {
                putExtra("finishState", GO_TO_WAITING_ROOM)
            }
            joinCodeViewModel.joinCodeState.observe(this,EventObserver{
                startActivity(intent)
                setResult(RESULT_OK,resultIntent)
                finish()
            })
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    companion object{
        const val GO_TO_WAITING_ROOM = true
        const val BACK_TO_JOIN_CODE = false
    }
}

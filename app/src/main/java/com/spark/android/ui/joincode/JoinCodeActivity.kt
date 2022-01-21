package com.spark.android.ui.joincode

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.spark.android.R
import com.spark.android.data.remote.entity.response.JoinCodeRoomInfoResponse
import com.spark.android.databinding.ActivityJoinCodeBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.joincode.viewmodel.JoinCodeViewModel
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.storage.StoragePhotoCollectionActivity
import com.spark.android.ui.waitingroom.WaitingRoomActivity
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
        roomInfo = intent.getParcelableExtra<JoinCodeRoomInfoResponse>("roomInfo")!!
    }

    private fun initInputCodeAgainButtonListener() {
        binding.btnJoinCodeInputAgain.setOnClickListener {
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
                this.putExtra("roomId", roomInfo.roomId)
                putExtra("startPoint", WaitingRoomActivity.START_FROM_JOIN_CODE)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }
}

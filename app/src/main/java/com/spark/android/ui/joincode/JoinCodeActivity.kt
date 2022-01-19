package com.spark.android.ui.joincode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.data.remote.entity.response.JoinCodeRoomInfoResponse
import com.spark.android.databinding.ActivityJoinCodeBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.waitingroom.WaitingRoomActivity

class JoinCodeActivity : BaseActivity<ActivityJoinCodeBinding>(R.layout.activity_join_code){

    private lateinit var roomInfo : JoinCodeRoomInfoResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtra()
        binding.joinCodeRoomInfoResponse = roomInfo
        initInputCodeAgainButtonListener()
        initEnterWaitingRoomButtonListener()
    }

    private fun initExtra(){
        roomInfo = intent.getParcelableExtra<JoinCodeRoomInfoResponse>("roomInfo")!!
    }

    private fun initInputCodeAgainButtonListener(){
        binding.btnJoinCodeInputAgain.setOnClickListener {
            finish()
        }
    }

    private fun initEnterWaitingRoomButtonListener(){
        binding.btnJoinCodeEnterWaitingRoom.setOnClickListener {
            val intent = Intent(this,WaitingRoomActivity::class.java).apply {
                this.putExtra("roomId",roomInfo.roomId)
            }
            startActivity(intent)
            finish()
        }
    }
}
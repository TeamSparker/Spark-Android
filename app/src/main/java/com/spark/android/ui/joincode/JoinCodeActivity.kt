package com.spark.android.ui.joincode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityJoinCodeBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.waitingroom.WaitingRoomActivity

class JoinCodeActivity : BaseActivity<ActivityJoinCodeBinding>(R.layout.activity_join_code){

    private lateinit var roomCode :String
    val roomId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtra()
        initInputCodeAgainButtonListener()
        initEnterWaitingRoomButtonListener()
    }

    private fun initExtra(){
        roomCode = intent.getStringExtra("roomCode").toString()
    }

    private fun initInputCodeAgainButtonListener(){
        binding.btnJoinCodeInputAgain.setOnClickListener {
            finish()
        }
    }

    private fun initEnterWaitingRoomButtonListener(){
        binding.btnJoinCodeEnterWaitingRoom.setOnClickListener {
            val intent = Intent(this,WaitingRoomActivity::class.java).apply {
                this.putExtra("roomId",roomId)
            }
            startActivity(intent)
            finish()
        }
    }
}
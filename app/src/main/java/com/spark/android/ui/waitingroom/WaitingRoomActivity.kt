package com.spark.android.ui.waitingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityWaitingRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.setpurpose.SetPurposeFragment
import kotlin.properties.Delegates

class WaitingRoomActivity : BaseActivity<ActivityWaitingRoomBinding>(R.layout.activity_waiting_room) {

    private var roomId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtra()
        initTransactionEvent()
    }

    private fun initExtra(){
        roomId = intent.getIntExtra("roomId",-1)
    }

    private fun initTransactionEvent(){
        val waitingRoomFragment = WaitingRoomFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_waiting_room,waitingRoomFragment).commit()
    }
}
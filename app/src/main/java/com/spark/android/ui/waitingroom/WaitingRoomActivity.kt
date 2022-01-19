package com.spark.android.ui.waitingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityWaitingRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.setpurpose.SetPurposeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class WaitingRoomActivity :
    BaseActivity<ActivityWaitingRoomBinding>(R.layout.activity_waiting_room) {

    private var roomId by Delegates.notNull<Int>()
    private var startPoint by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtra()
        initTransactionEvent()
    }

    private fun initExtra() {
        roomId = intent.getIntExtra("roomId", -1)
        startPoint = intent.getBooleanExtra("startPoint",false)
    }

    private fun initTransactionEvent() {
        val waitingRoomFragment = WaitingRoomFragment()
        var bundle = Bundle()
        bundle.putInt("roomId", roomId)
        bundle.putBoolean("startPoint",startPoint)
        waitingRoomFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add(R.id.container_waiting_room, waitingRoomFragment).commit()
    }

    companion object {
        const val START_FROM_HOME = true
        const val START_FROM_CONFIRM_METHOD = false
        const val START_FROM_JOIN_CODE = false
    }

}
package com.spark.android.ui.waitingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityWaitingRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.setpurpose.SetPurposeFragment
import com.spark.android.ui.waitingroom.checkroom.CheckRoomFragment
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class WaitingRoomActivity :
    BaseActivity<ActivityWaitingRoomBinding>(R.layout.activity_waiting_room) {

    private var roomId by Delegates.notNull<Int>()
    private var startPoint by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBarStyle()
        initExtra()
        initTransactionEvent()
    }

    private fun initExtra() {
        roomId = intent.getIntExtra("roomId", -1)
        startPoint = intent.getBooleanExtra("startPoint",false)
    }

    private fun initTransactionEvent() {
        val waitingRoomFragment = WaitingRoomFragment()
        val checkRoomFragment = CheckRoomFragment()
        var bundle = Bundle()
        bundle.putInt("roomId", roomId)
        bundle.putBoolean("startPoint",startPoint)
        waitingRoomFragment.arguments = bundle
        checkRoomFragment.arguments = bundle

        if(startPoint) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_waiting_room, waitingRoomFragment).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_waiting_room, checkRoomFragment).commit()
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    companion object {
        const val START_FROM_HOME = true
        const val START_FROM_CONFIRM_METHOD = false
        const val START_FROM_JOIN_CODE = false
    }

}
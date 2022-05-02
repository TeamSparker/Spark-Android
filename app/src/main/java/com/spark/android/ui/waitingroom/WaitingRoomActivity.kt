package com.spark.android.ui.waitingroom

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityWaitingRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.waitingroom.checkroom.CheckRoomFragment
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class WaitingRoomActivity :
    BaseActivity<ActivityWaitingRoomBinding>(R.layout.activity_waiting_room) {

    private var roomId by Delegates.notNull<Int>()
    private var startPoint by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBarStyle()
        initExtra()
        initTransactionEvent()
    }

    private fun initExtra() {
        roomId = intent.getIntExtra("roomId", -1)
        startPoint = intent.getIntExtra("startPoint", START_FROM_HOME)
    }

    private fun initTransactionEvent() {

        var bundle = Bundle()
        bundle.putInt("roomId", roomId)
        bundle.putInt("startPoint", startPoint)

        if (startPoint == START_FROM_HOME || startPoint == START_FROM_JOIN_CODE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, WaitingRoomFragment::class.java, bundle)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, CheckRoomFragment::class.java, bundle)
                .commit()
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    companion object {
        const val START_FROM_HOME = 1
        const val START_FROM_CONFIRM_METHOD = 2
        const val START_FROM_JOIN_CODE = 3
    }
}

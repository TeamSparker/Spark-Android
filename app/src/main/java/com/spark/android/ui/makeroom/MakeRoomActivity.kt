package com.spark.android.ui.makeroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.spark.android.R
import com.spark.android.databinding.ActivityMakeRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.makeroom.namesetting.NameSettingFragment
import com.spark.android.ui.makeroom.selectconfirmmethod.SelectConfirmMethodFragment
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.ui.waitingroom.adapter.WaitingRoomRecyclerViewAdapter

class MakeRoomActivity : BaseActivity<ActivityMakeRoomBinding>(R.layout.activity_make_room) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //프래그먼트 실험용 코드 (추후에 삭제할꺼임)
        val waitingRoomFragment = WaitingRoomFragment()
        val nameSettingFragment = NameSettingFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_make_room, nameSettingFragment)
            .commit()
    }
}
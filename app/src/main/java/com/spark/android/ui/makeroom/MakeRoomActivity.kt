package com.spark.android.ui.makeroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.spark.android.R
import com.spark.android.databinding.ActivityMakeRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.makeroom.namesetting.NameSettingFragment
import com.spark.android.ui.makeroom.selectconfirmmethod.SelectConfirmMethodFragment
import com.spark.android.ui.setpurpose.SetPurposeFragment
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.ui.waitingroom.adapter.WaitingRoomRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeRoomActivity : BaseActivity<ActivityMakeRoomBinding>(R.layout.activity_make_room) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
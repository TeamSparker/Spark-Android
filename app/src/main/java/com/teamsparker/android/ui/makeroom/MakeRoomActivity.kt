package com.teamsparker.android.ui.makeroom

import android.os.Bundle
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityMakeRoomBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeRoomActivity : BaseActivity<ActivityMakeRoomBinding>(R.layout.activity_make_room) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarStyle()
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }
}

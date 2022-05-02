package com.spark.android.ui.makeroom

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityMakeRoomBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
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

package com.spark.android.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityOnBoardingBinding
import com.spark.android.ui.base.BaseActivity

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
    }
}
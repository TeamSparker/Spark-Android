package com.spark.android.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityOnBoardingBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.onboarding.adapter.OnBoardingVpAdapter

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {

    private lateinit var onBoardingVpAdapter : OnBoardingVpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }


    private fun initAdapter(){
        val fragmentList = listOf(OnBoardingOneFragment(),OnBoardingTwoFragment(),OnBoardingThreeFragment(),OnBoardingFourFragment())
        onBoardingVpAdapter = OnBoardingVpAdapter(this)
        onBoardingVpAdapter.fragments.addAll(fragmentList)

        binding.vpOnBoarding.adapter = onBoardingVpAdapter
    }
}
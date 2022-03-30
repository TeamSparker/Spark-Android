package com.spark.android.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.spark.android.R
import com.spark.android.databinding.ActivityOnBoardingBinding
import com.spark.android.ui.auth.AuthActivity
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.onboarding.adapter.OnBoardingVpAdapter

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    private lateinit var onBoardingVpAdapter: OnBoardingVpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val onBoardingPageList = listOf(
            OnBoardingOneFragment(),
            OnBoardingTwoFragment(),
            OnBoardingThreeFragment(),
            OnBoardingFourFragment()
        )
        onBoardingVpAdapter = OnBoardingVpAdapter(this)
        onBoardingVpAdapter.fragments.addAll(onBoardingPageList)

        binding.vpOnBoarding.adapter = onBoardingVpAdapter
        binding.indicatorOnBoarding.setViewPager(binding.vpOnBoarding)

        binding.vpOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val isLastPage = position == onBoardingPageList.size -1
                binding.btnOnboardingSkip.visibility = if (isLastPage) View.INVISIBLE else View.VISIBLE
                binding.btnOnboardingStart.visibility = if (!isLastPage) View.INVISIBLE else View.VISIBLE
            }
        })

        binding.btnOnboardingSkip.setOnClickListener {
            startActivity(Intent(this,AuthActivity::class.java))
            finish()
        }
        binding.btnOnboardingStart.setOnClickListener {
            startActivity(Intent(this,AuthActivity::class.java))
            finish()
        }
    }
}
package com.teamsparker.android.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityOnBoardingBinding
import com.teamsparker.android.ui.auth.AuthActivity
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.onboarding.adapter.OnBoardingVpAdapter
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    private lateinit var onBoardingVpAdapter: OnBoardingVpAdapter
    private lateinit var onBoardingPageList: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarStyle()
        initOnBoardingVpAdapter()
        initOnBoardingVpIndicator()
        setBtnVisibilityOnLastPage()
        initSkipBtn()
        initStartBtn()
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    private fun initOnBoardingVpAdapter() {
        onBoardingPageList = listOf(
            OnBoardingOneFragment(),
            OnBoardingTwoFragment(),
            OnBoardingThreeFragment(),
            OnBoardingFourFragment()
        )
        onBoardingVpAdapter = OnBoardingVpAdapter(this)
        onBoardingVpAdapter.fragments.addAll(onBoardingPageList)

        binding.vpOnBoarding.adapter = onBoardingVpAdapter
    }

    private fun initOnBoardingVpIndicator(){
        binding.indicatorOnBoarding.setViewPager(binding.vpOnBoarding)
    }

    private fun setBtnVisibilityOnLastPage() {
        binding.vpOnBoarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val isLastPage = position == onBoardingPageList.size - 1
                binding.btnOnboardingSkip.visibility =
                    if (isLastPage) View.INVISIBLE else View.VISIBLE
                binding.btnOnboardingStart.visibility =
                    if (isLastPage) View.VISIBLE else View.INVISIBLE
            }
        })
    }

    private fun initSkipBtn() {
        binding.btnOnboardingSkip.setOnClickListener {
            binding.vpOnBoarding.currentItem = 3
        }
    }

    private fun initStartBtn() {
        binding.btnOnboardingStart.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}

package com.spark.android.ui.intro

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityIntroBinding
import com.spark.android.ui.auth.AuthActivity
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.main.MainActivity
import com.spark.android.util.initStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    private val introViewModel by viewModels<IntroViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarColor(R.color.spark_black)
        initLottieListener()
        startSplashLottie()
    }

    private fun startSplashLottie() {
        binding.lottieIntro.playAnimation()
    }

    private fun initLottieListener() {
        binding.lottieIntro.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                if (introViewModel.hasToken()) {
                    moveToMainActivity()
                } else {
                    moveToAuthActivity()
                }
            }

            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    private fun moveToAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        })
        finish()
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        })
        finish()
    }
}

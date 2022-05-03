package com.teamsparker.android.ui.intro

import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.SparkMessagingService.Companion.OPEN_FROM_PUSH_ALARM
import com.teamsparker.android.databinding.ActivityIntroBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.ui.onboarding.OnBoardingActivity
import com.teamsparker.android.util.DialogUtil
import com.teamsparker.android.util.DialogUtil.Companion.UPDATE_CHECK
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.initStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    private val introViewModel by viewModels<IntroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarColor(R.color.spark_more_deep_gray)
        checkOpenFromPushAlarm()
        introViewModel.versionCheck()
        initVersionUpdateStateObserver()
        initFcmTokenObserver()
        initIsDoneObserver()
        initLottieListener()
        startSplashLottie()
    }

    private fun checkOpenFromPushAlarm() {
        intent.getStringExtra(OPEN_FROM_PUSH_ALARM)?.let {
            FirebaseLogUtil.logNotificationOpenEvent(it)
        }
    }

    private fun initVersionUpdateStateObserver() {
        introViewModel.versionUpdateState.observe(this) { state ->
            if (state == VersionUpdateState.FORCE) {
                DialogUtil(UPDATE_CHECK) {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_update)))
                    )
                    finish()
                }.show(supportFragmentManager, this.javaClass.name)
            } else {
                introViewModel.initFcmToken()
            }
        }
    }

    private fun initFcmTokenObserver() {
        introViewModel.fcmToken.observe(this) { token ->
            if (token.isNotBlank()) {
                introViewModel.getFcmToken()
            }
        }
    }

    private fun initIsDoneObserver() {
        introViewModel.isDone.observe(this) { isDone ->
            if (isDone) {
                if (introViewModel.hasToken()) {
                    moveToMainActivity()
                } else {
                    moveToOnBoardingActivity()
                }
            }
        }
    }

    private fun startSplashLottie() {
        binding.lottieIntroBg.playAnimation()
        binding.lottieIntroLogo.playAnimation()
    }

    private fun initLottieListener() {
        binding.lottieIntroBg.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                introViewModel.initIsEndLottie()
            }

            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        })
        finish()
    }

    private fun moveToOnBoardingActivity() {
        startActivity(Intent(this, OnBoardingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        })
        finish()
    }
}

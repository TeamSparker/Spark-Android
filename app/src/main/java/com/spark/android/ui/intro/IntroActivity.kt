package com.spark.android.ui.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.spark.android.R
import com.spark.android.databinding.ActivityIntroBinding
import com.spark.android.ui.MainActivity
import com.spark.android.ui.base.BaseActivity
import com.spark.android.util.initStatusBarColor

class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarColor(R.color.spark_black)
        moveToMain()
    }

    private fun moveToMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, DURATION)
    }

    companion object {
        private const val DURATION: Long = 3000
    }
}

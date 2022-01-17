package com.spark.android.ui.certify

import android.content.Intent
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityCertifyBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.timer.TimerStartActivity

class CertifyActivity : BaseActivity<ActivityCertifyBinding>(R.layout.activity_certify) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCertifyBackBtnClickListener()
        initCertifyQuitBtnClickListener()
        initCertifyPhotoBtnClickListener()
    }

    private fun initCertifyBackBtnClickListener() {
        binding.btnCertifyBack.setOnClickListener {
            val intent = Intent(this, TimerStartActivity::class.java)
            // put extra
            startActivity(intent)
            finish()
        }
    }

    private fun initCertifyQuitBtnClickListener() {
        binding.btnCertifyQuit.setOnClickListener {
            finish()
        }
    }

    private fun initCertifyPhotoBtnClickListener() {
        binding.btnCertifyPhoto.setOnClickListener {
            CertifyBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }
}
package com.spark.android.ui.certify

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityCertifyBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.CertifyMode.Companion.NORMAL_MODE
import com.spark.android.ui.certify.CertifyMode.Companion.NORMAL_READY_MODE
import com.spark.android.ui.certify.CertifyMode.Companion.ONLY_CAMERA_MODE
import com.spark.android.ui.certify.viewmodel.CertifyViewModel
import com.spark.android.ui.timer.TimerStartActivity
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.RETURN_CERTIFY_TIMER
import com.spark.android.util.DialogUtil.Companion.STOP_CERTIFY_PHOTO
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite

class CertifyActivity : BaseActivity<ActivityCertifyBinding>(R.layout.activity_certify) {
    private val certifyViewModel by viewModels<CertifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.certifyViewModel = certifyViewModel

        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
        initCertifyMode()
        initCertifyBackBtnClickListener()
        initCertifyQuitBtnClickListener()
        initCertifyPhotoBtnClickListener()
    }

    private fun initCertifyMode() {
        certifyViewModel.initCertifyMode(ONLY_CAMERA_MODE)
//        certifyViewModel.initOnlyCamera(intent.getBooleanExtra("onlyCamera",false))
    }

    private fun moveToTimerActivity() {
        val intent = Intent(this, TimerStartActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        // put extra
        startActivity(intent)
        finish()
    }

    private fun showStopCertifyPhotoDialog() {
        DialogUtil(STOP_CERTIFY_PHOTO) {
            finish()
        }.show(supportFragmentManager, this.javaClass.name)
    }

    private fun showReturnCertifyTimerDialog() {
        DialogUtil(RETURN_CERTIFY_TIMER) {
            moveToTimerActivity()
        }.show(supportFragmentManager, this.javaClass.name)
    }

    private fun initCertifyBackBtnClickListener() {
        binding.btnCertifyBack.setOnClickListener {
            when (certifyViewModel.certifyMode.value) {
                NORMAL_READY_MODE -> {
                    moveToTimerActivity()
                }
                NORMAL_MODE -> {
                    showReturnCertifyTimerDialog()
                }
                ONLY_CAMERA_MODE -> {
                    showStopCertifyPhotoDialog()
                }
            }
        }
    }

    private fun initCertifyQuitBtnClickListener() {
        binding.btnCertifyQuit.setOnClickListener {
            showStopCertifyPhotoDialog()
        }
    }

    private fun initCertifyPhotoBtnClickListener() {
        binding.btnCertifyPhoto.setOnClickListener {
            CertifyBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        val intent = Intent(this, TimerStartActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        // put extra
        startActivity(intent)
        finish()
    }
}
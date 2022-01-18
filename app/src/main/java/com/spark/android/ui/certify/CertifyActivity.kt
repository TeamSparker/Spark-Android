package com.spark.android.ui.certify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityCertifyBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.viewmodel.CertifyViewModel
import com.spark.android.ui.timer.TimerStartActivity
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.STOP_CERTIFY_PHOTO
import com.spark.android.util.initStatusBarColor

class CertifyActivity : BaseActivity<ActivityCertifyBinding>(R.layout.activity_certify) {
    private val certifyViewModel by viewModels<CertifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.certifyViewModel = certifyViewModel

        initStatusBarColor(R.color.spark_white)
        initOnlyCamera()
        initIsEditing()
        initCertifyBackBtnClickListener()
        initCertifyQuitBtnClickListener()
        initCertifyPhotoBtnClickListener()
    }

    private fun initOnlyCamera() {
        certifyViewModel.initOnlyCamera(false)
//        certifyViewModel.initOnlyCamera(intent.getBooleanExtra("onlyCamera",false))
    }

    private fun initIsEditing() {
        certifyViewModel.initIsEditing(false)
    }

    private fun initCertifyBackBtnClickListener() {
        binding.btnCertifyBack.setOnClickListener {
            val intent = Intent(this, TimerStartActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                putExtra("myVisible",View.VISIBLE)
            }
            // put extra
            startActivity(intent)
            finish()
        }
    }

    private fun initCertifyQuitBtnClickListener() {
        binding.btnCertifyQuit.setOnClickListener {
            DialogUtil(STOP_CERTIFY_PHOTO) {
                finish()
            }.show(supportFragmentManager, this.javaClass.name)
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
}
package com.spark.android.ui.certify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityCertifyBinding
import com.spark.android.util.MultiPartResolver
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.CertifyMode.Companion.NORMAL_MODE
import com.spark.android.ui.certify.CertifyMode.Companion.NORMAL_READY_MODE
import com.spark.android.ui.certify.CertifyMode.Companion.ONLY_CAMERA_MODE
import com.spark.android.ui.certify.viewmodel.CertifyViewModel
import com.spark.android.ui.timer.TimerStartActivity
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.STOP_CERTIFY_PHOTO
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite

class CertifyActivity : BaseActivity<ActivityCertifyBinding>(R.layout.activity_certify) {
    private val certifyViewModel by viewModels<CertifyViewModel>()
    private val multiPartResolver = MultiPartResolver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.certifyViewModel = certifyViewModel

        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
        initIntentData()
        initImgUriObserver()
        initImgBitmapObserver()
        initCertifyBackBtnClickListener()
        initCertifyQuitBtnClickListener()
        initCertifyPhotoBtnClickListener()
        initCertifyPhotoAgainBtnClickListener()
        initCertifyPhotoUploadBtnClickListener()
    }

    private fun initIntentData() {
        certifyViewModel.initTimerRecord(intent.getStringExtra("timerRecord").toString())
        certifyViewModel.initRoomName(intent.getStringExtra("roomName").toString())

        val fromStart = intent.getBooleanExtra("fromStart", true)
        if(fromStart) {
            certifyViewModel.initCertifyMode(NORMAL_READY_MODE)
        } else {
            certifyViewModel.initCertifyMode(ONLY_CAMERA_MODE)
        }
    }

    private fun initImgUriObserver() {
        certifyViewModel.imgUri.observe(this) { uri ->
            uri?.let {
                certifyViewModel.initCertifyImgMultiPart(multiPartResolver.createImgMultiPart(it))
            }
        }
    }

    private fun initImgBitmapObserver() {
        certifyViewModel.imgBitmap.observe(this) { bitmap ->
            bitmap?.let {
                certifyViewModel.initCertifyImgMultiPart(multiPartResolver.createImgMultiPart(it))
            }
        }
    }

    private fun moveToTimerActivity() {
        val intent = Intent(this, TimerStartActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            putExtra("roomName", certifyViewModel.roomName.value.toString())
            putExtra("timerRecord", certifyViewModel.timerRecord.value.toString())
            putExtra("myVisible",View.VISIBLE)
            putExtra("myInvisible", View.INVISIBLE)
        }
        startActivity(intent)
        finish()
    }

    private fun showStopCertifyPhotoDialog() {
        DialogUtil(STOP_CERTIFY_PHOTO) {
            finish()
        }.show(supportFragmentManager, this.javaClass.name)
    }

    private fun initCertifyBackBtnClickListener() {
        binding.btnCertifyBack.setOnClickListener {
            when (certifyViewModel.certifyMode.value) {
                NORMAL_READY_MODE, NORMAL_MODE -> {
                    moveToTimerActivity()
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

    private fun initCertifyPhotoAgainBtnClickListener() {
        binding.btnCertifyPhotoAgain.setOnClickListener {
            CertifyBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun initCertifyPhotoUploadBtnClickListener() {
        binding.btnCertifyPhotoUpload.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        moveToTimerActivity()
    }
}

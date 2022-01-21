package com.spark.android.ui.certify

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityCertifyBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.CertifyMode.Companion.NORMAL_MODE
import com.spark.android.ui.certify.CertifyMode.Companion.NORMAL_READY_MODE
import com.spark.android.ui.certify.CertifyMode.Companion.ONLY_CAMERA_MODE
import com.spark.android.ui.certify.viewmodel.CertifyViewModel
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.main.MainActivity.Companion.FROM_WHERE
import com.spark.android.ui.share.InstaShareDialogFragment
import com.spark.android.ui.share.InstaShareDialogFragment.Companion.INSTA_DIALOG
import com.spark.android.ui.share.InstaShareDialogFragment.Companion.NO_SHARE
import com.spark.android.ui.share.InstaShareDialogFragment.Companion.SHARE
import com.spark.android.ui.share.InstaShareDialogFragment.Companion.SHARE_MODE
import com.spark.android.ui.timer.TimerStartActivity
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.STOP_CERTIFY_PHOTO
import com.spark.android.util.MultiPartResolver
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.ui.share.InstaActivity

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
        initIsSuccessCertifyObserver()
        initFragmentResultListener()
    }

    private fun initIntentData() {
        certifyViewModel.initTimerRecord(intent.getStringExtra("timerRecord").toString())
        certifyViewModel.initRoomName(intent.getStringExtra("roomName").toString())
        certifyViewModel.initRoomId(intent.getIntExtra("roomId", -1))

        certifyViewModel.initCertifyMode(intent.getIntExtra("certifyMode",
            CertifyMode.NORMAL_READY_MODE))
        certifyViewModel.initOnlyCameraInitial(intent.getBooleanExtra("onlyCameraInitial", false))
        intent.getParcelableExtra<Uri>("imgUri")?.let { certifyViewModel.initImgUri(it) }
        intent.getParcelableExtra<Bitmap>("imgBitmap")?.let { certifyViewModel.initImgBitmap(it) }

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
            putExtra("roomId", certifyViewModel.roomId.value)
            putExtra("timerRecord", certifyViewModel.timerRecord.value.toString())
            putExtra("myVisible", View.VISIBLE)
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
            certifyViewModel.postCertification()
        }
    }

    private fun initIsSuccessCertifyObserver() {
        certifyViewModel.isSuccessCertify.observe(this) { isSuccess ->
            if (isSuccess) {
                InstaShareDialogFragment().show(supportFragmentManager, this.javaClass.name)
            }
        }
    }

    private fun initFragmentResultListener() {
        supportFragmentManager.setFragmentResultListener(INSTA_DIALOG, this) { _, bundle ->
            when (bundle.get(SHARE_MODE)) {
                SHARE -> {
                    startActivity(Intent(this, InstaActivity::class.java).apply {
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        putExtra(FROM_WHERE, FROM_CERTIFY_ACTIVITY)
                        putExtra("nickname", "닉네임")
                        putExtra("roomName", certifyViewModel.roomName.value)
                        putExtra(
                            "profileImgUrl",
                            "https://console.firebase.google.com/project/we-sopt-spark/storage/we-sopt-spark.appspot.com/files/~2Fusers"
                        )
                        putExtra("certifyImgUri", certifyViewModel.imgUri.value)
                        putExtra("certifyImgBitmap", certifyViewModel.imgBitmap.value)
                        putExtra("timerRecord", certifyViewModel.timerRecord.value)
                    })
                }
                NO_SHARE -> {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra(FROM_WHERE, FROM_CERTIFY_ACTIVITY)
                    })
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        if(certifyViewModel.certifyMode.value == CertifyMode.ONLY_CAMERA_MODE) {
            showStopCertifyPhotoDialog()
        }else {
            moveToTimerActivity()
        }
    }

    companion object {
        const val FROM_CERTIFY_ACTIVITY = "CertifyActivity"
    }
}

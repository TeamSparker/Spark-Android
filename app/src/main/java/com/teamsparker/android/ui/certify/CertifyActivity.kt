package com.teamsparker.android.ui.certify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityCertifyBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.certify.CertifyMode.Companion.NORMAL_MODE
import com.teamsparker.android.ui.certify.CertifyMode.Companion.NORMAL_READY_MODE
import com.teamsparker.android.ui.certify.CertifyMode.Companion.ONLY_CAMERA_MODE
import com.teamsparker.android.ui.certify.viewmodel.CertifyViewModel
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.ui.main.MainActivity.Companion.FROM_WHERE
import com.teamsparker.android.ui.share.InstaActivity
import com.teamsparker.android.ui.share.InstaShareDialogFragment
import com.teamsparker.android.ui.share.InstaShareDialogFragment.Companion.INSTA_DIALOG
import com.teamsparker.android.ui.share.InstaShareDialogFragment.Companion.NO_SHARE
import com.teamsparker.android.ui.share.InstaShareDialogFragment.Companion.SHARE
import com.teamsparker.android.ui.share.InstaShareDialogFragment.Companion.SHARE_MODE
import com.teamsparker.android.ui.timer.TimerStartActivity
import com.teamsparker.android.ui.timer.viewmodel.TimerStartViewModel
import com.teamsparker.android.util.DialogUtil
import com.teamsparker.android.util.DialogUtil.Companion.STOP_CERTIFY_PHOTO
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_UPLOAD
import com.teamsparker.android.util.MultiPartResolver
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite

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
        initCertifyBackBtnClickListener()
        initCertifyQuitBtnClickListener()
        initCertifyPhotoBtnClickListener()
        initCertifyPhotoAgainBtnClickListener()
        initCertifyPhotoUploadBtnClickListener()
        initIsSuccessCertifyObserver()
        initFragmentResultListener()
    }

    private fun initIntentData() {
        certifyViewModel.initTimerRecord(intent.getStringExtra("timerRecord"))
        certifyViewModel.initRoomName(intent.getStringExtra("roomName").toString())
        certifyViewModel.initRoomId(intent.getIntExtra("roomId", -1))
        certifyViewModel.initNickName(intent.getStringExtra("nickname") ?: "")
        certifyViewModel.initCertifyMode(intent.getIntExtra("certifyMode", NORMAL_READY_MODE))
        certifyViewModel.initOnlyCameraInitial(intent.getBooleanExtra("onlyCameraInitial", false))
        certifyViewModel.initFromCamera(intent.getBooleanExtra("fromCamera", false))
        intent.getStringExtra("profileImgUrl")?.let { certifyViewModel.initProfileImg(it) }
        intent.getParcelableExtra<Uri>("imgUri")?.let { certifyViewModel.initImgUri(it) }
    }

    private fun initImgUriObserver() {
        certifyViewModel.imgUri.observe(this) { uri ->
            uri?.let {
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
            putExtra("comeBackTimerState", TimerStartViewModel.TIMER_PAUSE)
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
            FirebaseLogUtil.logClickEvent(CLICK_UPLOAD)
            certifyViewModel.postCertification()
        }
    }

    private fun initIsSuccessCertifyObserver() {
        certifyViewModel.isSuccessCertify.observe(this) { isSuccess ->
            if (isSuccess) {
                InstaShareDialogFragment().apply {
                    arguments = Bundle().apply {
                        putInt("leftDay", intent.getIntExtra("leftDay", -1))
                    }
                }.show(supportFragmentManager, this.javaClass.name)
            }
        }
    }

    private fun initFragmentResultListener() {
        supportFragmentManager.setFragmentResultListener(INSTA_DIALOG, this) { _, bundle ->
            when (bundle.get(SHARE_MODE)) {
                SHARE -> {
                    startActivity(Intent(this, InstaActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra(FROM_WHERE, FROM_CERTIFY_ACTIVITY)
                        putExtra("nickname", certifyViewModel.nickName.value)
                        putExtra("roomName", certifyViewModel.roomName.value)
                        putExtra("profileImgUrl", certifyViewModel.profileImg.value)
                        putExtra("certifyImgUri", certifyViewModel.imgUri.value)
                        putExtra("timerRecord", certifyViewModel.timerRecord.value)
                        putExtra("fromCamera", certifyViewModel.fromCamera)
                    })
                }
                NO_SHARE -> {
                    if (certifyViewModel.fromCamera) {
                        contentResolver.delete(
                            requireNotNull(certifyViewModel.imgUri.value), null, null
                        )
                    }
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
        if (certifyViewModel.certifyMode.value == CertifyMode.ONLY_CAMERA_MODE) {
            showStopCertifyPhotoDialog()
        } else {
            moveToTimerActivity()
        }
    }

    companion object {
        const val FROM_CERTIFY_ACTIVITY = "CertifyActivity"
    }
}

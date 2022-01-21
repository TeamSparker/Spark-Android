package com.spark.android.ui.share

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.spark.android.R
import com.spark.android.databinding.ActivityInstaBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.CertifyActivity.Companion.FROM_CERTIFY_ACTIVITY
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.main.MainActivity.Companion.FROM_WHERE
import com.spark.android.util.showToast

class InstaActivity : BaseActivity<ActivityInstaBinding>(R.layout.activity_insta) {
    private lateinit var imgUri: Uri
    private val instaShareActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        moveToFeed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.nickname = intent.getStringExtra("nickname")
        binding.roomName = intent.getStringExtra("roomName")
        binding.profileImgUrl = intent.getStringExtra("profileImgUrl")
        binding.timerRecord = intent.getStringExtra("timerRecord")
        intent.getParcelableExtra<Uri>("certifyImgUri")?.let {
            imgUri = it
            binding.ivInstaStickerCertifyImg.setImageURI(it)
        }
        intent.getParcelableExtra<Bitmap>("certifyImgBitmap")?.let {
            binding.ivInstaStickerCertifyImg.setImageBitmap(it)
        }
        shareInsta()
    }

    private fun moveToFeed() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(FROM_WHERE, FROM_CERTIFY_ACTIVITY)
        })
    }

    private fun shareInsta() {
        val stickerBackgroundUri = imgUri

        val sourceApplication = "com.spark.android"

        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.type = "image/jpeg"
        intent.putExtra("source_application", sourceApplication)
        intent.putExtra("top_background_color", "#c6c6c6")
        intent.putExtra("bottom_background_color", "#616161")
        intent.putExtra("interactive_asset_uri", stickerBackgroundUri)

        grantUriPermission(
            "com.instagram.android",
            stickerBackgroundUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        try {
            instaShareActivityLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(getString(R.string.insta_error_msg))
            moveToFeed()
            Log.d("Insta_Share", "Instagram 앱이 없습니다.")
        }

    }
}

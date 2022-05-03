package com.teamsparker.android.ui.share

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityInstaBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.certify.CertifyActivity.Companion.FROM_CERTIFY_ACTIVITY
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.ui.main.MainActivity.Companion.FROM_WHERE
import com.teamsparker.android.util.showToast
import android.provider.MediaStore
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.Calendar

class InstaActivity : BaseActivity<ActivityInstaBinding>(R.layout.activity_insta) {
    private lateinit var imgUri: Uri
    private lateinit var instaFeedUri: Uri
    private val instaShareActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        contentResolver.delete(instaFeedUri, null, null)
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

        binding.layoutInstaStickerBg.post {
            instaFeedUri = getImageUri(viewToBitmap(binding.layoutInstaStickerBg))!!
            shareInsta(instaFeedUri)
        }
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        view.draw(canvas)
        return bitmap
    }

    private fun getImageUri(bitmap: Bitmap): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            var outputStream: OutputStream? = null
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
            val uri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                outputStream = contentResolver.openOutputStream(it)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                contentValues.clear()
                contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
                contentResolver.update(it, contentValues, null, null)
            }

            return uri
        } else {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            return Uri.parse(
                MediaStore.Images.Media.insertImage(
                    contentResolver,
                    bitmap,
                    "InstaShareLayer-${Calendar.getInstance().time}",
                    null
                )
            )
        }
    }

    private fun moveToFeed() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(FROM_WHERE, FROM_CERTIFY_ACTIVITY)
        })
    }

    private fun shareInsta(uri: Uri) {

        val sourceApplication = "com.spark.android"

        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.type = "image/jpeg"
        intent.putExtra("source_application", sourceApplication)
        intent.putExtra("top_background_color", "#737376")
        intent.putExtra("bottom_background_color", "#737376")
        intent.putExtra("interactive_asset_uri", uri)

        grantUriPermission(
            "com.instagram.android",
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        try {
            instaShareActivityLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(getString(R.string.insta_error_msg))
            moveToFeed()
            Timber.tag("Insta_Share").d("Instagram 앱이 없습니다.")
        }
    }
}

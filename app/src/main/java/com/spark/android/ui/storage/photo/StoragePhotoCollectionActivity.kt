package com.spark.android.ui.storage.photo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoCollectionBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.storage.adapter.PhotoCollectionRvAdapter
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.isReOpening
import com.spark.android.ui.storage.viewmodel.PhotoViewModel
import com.spark.android.util.initStatusBarColor

class StoragePhotoCollectionActivity :
    BaseActivity<ActivityStoragePhotoCollectionBinding>(R.layout.activity_storage_photo_collection) {
    private val photoCollectionRvAdapter = PhotoCollectionRvAdapter()
    private val photoCollectionViewModel by viewModels<PhotoViewModel>()
    private var roomId = 0
    private var thumbnail : String? = ""
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getIntExtra("roomId", -1)
        initActivityResultLauncher()
        thumbnail = intent.getStringExtra("thumbnail")
        //Log.d("개씨발","${thumbnail}")

        photoCollectionViewModel.initGetPhotoCollectionNetwork(roomId, -1, 70)
        binding.photoCollectionViewModel = photoCollectionViewModel
        initStatusBarStyle()
        setOnBackBtnClickListener()
        initStoragePhotoCollectionRvAdapter()
        initPhotoCollectionObserver()
        initPhotoCollectionMoreBtnClickListener()
    }

    private fun initActivityResultLauncher(){
         activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    thumbnail = intent.getStringExtra("thumbnail")
                    //Log.d("개씨발","${thumbnail}")
                }
            }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_black)
    }

    private fun initStoragePhotoCollectionRvAdapter() {
        binding.rvStoragePhotoCollection.adapter = photoCollectionRvAdapter
    }

    private fun initPhotoCollectionObserver() {
        photoCollectionViewModel.photoList.observe(this) { photo ->
            photoCollectionRvAdapter.setList(photo)
        }
    }

    private fun setOnBackBtnClickListener() {
        binding.btnStoragePhotoCollectionBackWhite.setOnClickListener {
            isReOpening = false
            moveToMain()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveToMain()
    }

    private fun initPhotoCollectionMoreBtnClickListener() {
        binding.btnStoragePhotoCollectionMoreWhite.setOnClickListener {
            PhotoCollectionMoreBottomSheet().apply {
                setChangePhotoBtnClickListener {
                    val intent = Intent(context, StoragePhotoMainPickActivity::class.java)
                    activityResultLauncher.launch(intent.apply {
                        putExtra("roomId", roomId)
                        putExtra("thumbnail",thumbnail)
                    })
                }
            }.show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.FROM_WHERE, FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY)
        })
    }

    companion object {
        const val FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY = "StoragePhotoCollectionActivity"
    }
}

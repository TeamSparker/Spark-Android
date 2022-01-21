package com.spark.android.ui.storage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoCollectionBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.storage.adapter.PhotoCollectionRvAdapter
import com.spark.android.ui.storage.viewmodel.PhotoCollectionViewModel

class StoragePhotoCollectionActivity :
    BaseActivity<ActivityStoragePhotoCollectionBinding>(R.layout.activity_storage_photo_collection) {
    private val photoCollectionRvAdapter = PhotoCollectionRvAdapter()
    private val photoCollectionViewModel by viewModels<PhotoCollectionViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val roomId = intent.getIntExtra("roomId", -1)
        photoCollectionViewModel.initPhotoCollectionNetwork(roomId, -1, 70)
        binding.photoCollectionViewModel = photoCollectionViewModel
        setOnBackBtnClickListener()
        initStoragePhotoCollectionRvAdapter()
        initPhotoCollectionObserver()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveToMain()
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
        binding.ivStoragePhotoCollectionBackWhite.setOnClickListener {
            moveToMain()
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

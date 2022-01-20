package com.spark.android.ui.storage

import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoCollectionBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.storage.adapter.PhotoCollectionRvAdapter
import com.spark.android.ui.storage.viewmodel.PhotoCollectionViewModel

class StoragePhotoCollectionActivity :
    BaseActivity<ActivityStoragePhotoCollectionBinding>(R.layout.activity_storage_photo_collection) {
    private val photoCollectionRvAdapter = PhotoCollectionRvAdapter()
    private val photoCollectionViewModel by viewModels<PhotoCollectionViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val roomId = intent.getIntExtra("roomId", -1)
        photoCollectionViewModel.initPhotoCollectionNetwork(roomId, -1, 5)
        binding.photoCollectionViewModel = photoCollectionViewModel
        binding.ivStoragePhotoCollectionBackWhite.setOnClickListener {
            finish()
        }
        initStoragePhotoCollectionRvAdapter()
        initPhotoCollectionObserver()
    }

    private fun initStoragePhotoCollectionRvAdapter() {
        binding.rvStoragePhotoCollection.adapter = photoCollectionRvAdapter
    }

    private fun initPhotoCollectionObserver() {
        photoCollectionViewModel.photoList.observe(this) { photo ->
            photoCollectionRvAdapter.setList(photo)

        }
    }
}
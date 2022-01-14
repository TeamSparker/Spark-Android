package com.spark.android.ui.storage

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoCollectionBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.storage.adapter.PhotoCollectionRvAdapter

class StoragePhotoCollectionActivity :
    BaseActivity<ActivityStoragePhotoCollectionBinding>(R.layout.activity_storage_photo_collection) {
    private val photoCollectionRvAdapter = PhotoCollectionRvAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStoragePhotoCollectionRvAdapter()
    }

    private fun initStoragePhotoCollectionRvAdapter() {
        photoCollectionRvAdapter.setList(listOf("D-day", "D-1", "D-2", "D-3", "D-4", "D-5"))
    }
}
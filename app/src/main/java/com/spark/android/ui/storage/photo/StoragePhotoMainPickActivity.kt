package com.spark.android.ui.storage.photo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoMainPickBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.selectedItemPos
import com.spark.android.ui.storage.viewmodel.PhotoViewModel
import com.spark.android.util.initStatusBarColor

class StoragePhotoMainPickActivity :
    BaseActivity<ActivityStoragePhotoMainPickBinding>(R.layout.activity_storage_photo_main_pick) {
    private val photoMainPickRvAdapter = PhotoMainPickRvAdapter(::onPhotoClick)
    private val photoMainPickViewModel by viewModels<PhotoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val roomId = intent.getIntExtra("roomId", -1)
        photoMainPickViewModel.initPhotoCollectionNetwork(roomId, -1, 70)
        binding.mainPhotoPickViewModel = photoMainPickViewModel
        initStatusBarStyle()
        setOnBackBtnClickListener()
        initStorageMainPhotoPickRvAdapter()
        initMainPhotoPickObserver()
        initMainPhotoPickBackBtnClickListener()
    }

    private fun onPhotoClick(position: Int) {
        Log.d("msg", "onPhotoCLick 작동함")
        if (photoMainPickViewModel.isSelectable(position)) {
            selectedItemPos = position
            for (i in 0..photoMainPickRvAdapter.photolist.size) photoMainPickRvAdapter.notifyItemChanged(i)
        }
    }

    private fun initStorageMainPhotoPickRvAdapter() {
        binding.rvStorageMainPhotoPick.adapter = photoMainPickRvAdapter
    }

    private fun setOnBackBtnClickListener() {

    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_black)
    }

    private fun initMainPhotoPickObserver() {
        photoMainPickViewModel.photoList.observe(this) { photo ->
            photoMainPickRvAdapter.setList(photo)
        }
    }

    private fun initMainPhotoPickBackBtnClickListener() {
        binding.btnCardMainPhotoPickBackWhite.setOnClickListener {
            finish()
        }

        binding.tvCardMainPhotoPickComplete.setOnClickListener {
            finish()
        }
    }
}

package com.spark.android.ui.storage.photo

import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoMainPickBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter
import com.spark.android.ui.storage.viewmodel.PhotoCollectionViewModel
import com.spark.android.util.initStatusBarColor

class StoragePhotoMainPickActivity :
    BaseActivity<ActivityStoragePhotoMainPickBinding>(R.layout.activity_storage_photo_main_pick) {
    private val mainPhotoPickRvAdapter = PhotoMainPickRvAdapter()
    private val mainPhotoPickViewModel by viewModels<PhotoCollectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val roomId = intent.getIntExtra("roomId",-1)
        mainPhotoPickViewModel.initPhotoCollectionNetwork(roomId,-1,70)
        binding.mainPhotoPickViewModel = mainPhotoPickViewModel
        initStatusBarStyle()
        setOnBackBtnClickListener()
        initStorageMainPhotoPickRvAdapter()
        initMainPhotoPickObserver()
        initMainPhotoPickBackBtnClickListener()
    }

    private fun initStorageMainPhotoPickRvAdapter() {
        binding.rvStorageMainPhotoPick.adapter = mainPhotoPickRvAdapter
    }

    private fun setOnBackBtnClickListener() {

    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_black)
    }

    private fun initMainPhotoPickObserver() {
        mainPhotoPickViewModel.photoList.observe(this){
            photo -> mainPhotoPickRvAdapter.setList(photo)
        }
    }

    private fun initMainPhotoPickBackBtnClickListener(){
        binding.btnCardMainPhotoPickBackWhite.setOnClickListener{
            finish()
        }

        binding.tvCardMainPhotoPickComplete.setOnClickListener {
            finish()
        }
    }
}


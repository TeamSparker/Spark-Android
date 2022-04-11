package com.spark.android.ui.storage.photo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoMainPickBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.selectedItemPos
import com.spark.android.ui.storage.viewmodel.PhotoViewModel
import com.spark.android.util.initStatusBarColor
import kotlin.properties.Delegates

class StoragePhotoMainPickActivity :
    BaseActivity<ActivityStoragePhotoMainPickBinding>(R.layout.activity_storage_photo_main_pick) {
    private val photoMainPickRvAdapter = PhotoMainPickRvAdapter(::onPhotoClick)
    private val photoMainPickViewModel by viewModels<PhotoViewModel>()
    private var roomId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getIntExtra("roomId", -1)
        photoMainPickViewModel.initPhotoCollectionNetwork(roomId, -1, 70)
        binding.mainPhotoPickViewModel = photoMainPickViewModel
        initStatusBarStyle()
        initStorageMainPhotoPickRvAdapter()
        initMainPhotoPickObserver()
        initMainPhotoPickBackBtnClickListener()
        initMainPhotoPickCompleteBtnClickListener()
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_black)
    }

    private fun initMainPhotoPickBackBtnClickListener() {
        binding.btnCardMainPhotoPickBackWhite.setOnClickListener {
            finish()
        }
    }

    private fun initMainPhotoPickCompleteBtnClickListener(){
        binding.tvCardMainPhotoPickComplete.setOnClickListener {
            photoMainPickViewModel.initPhotoMainNetwork()
            finish()
        }
    }

    private fun initStorageMainPhotoPickRvAdapter() {
        binding.rvStorageMainPhotoPick.adapter = photoMainPickRvAdapter
        //  selectedItemPos =  대표이미지 인덱스 : 서버한테 물어보기
    }

    private fun initMainPhotoPickObserver() {
        photoMainPickViewModel.photoList.observe(this) { photo ->
            photoMainPickRvAdapter.setList(photo.filter { it.status == "DONE" })
        }
    }

    private fun onPhotoClick(selectedPosition: Int, patchRecordId: Int) {
        Log.d("msg", "onPhotoCLick 작동함")
        if (photoMainPickViewModel.isSelectable(selectedPosition)) {
            val previousItemPos = selectedItemPos
            selectedItemPos = selectedPosition
            photoMainPickRvAdapter.notifyItemChanged(previousItemPos)
            photoMainPickRvAdapter.notifyItemChanged(selectedItemPos)
            photoMainPickViewModel.setPatchRecordId(patchRecordId)
            photoMainPickViewModel.setPatchRoomId(roomId)
        }
    }
}


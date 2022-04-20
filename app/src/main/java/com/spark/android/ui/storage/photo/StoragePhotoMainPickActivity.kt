package com.spark.android.ui.storage.photo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoMainPickBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.storage.StorageFragmentDirections
import com.spark.android.ui.storage.StorageMode
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.currentThumbnailItemPos
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.isInitialOpening
import com.spark.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.isReOpening
import com.spark.android.ui.storage.viewmodel.PhotoViewModel
import com.spark.android.ui.storage.viewmodel.StorageViewModel
import com.spark.android.util.initStatusBarColor
import kotlin.properties.Delegates

class StoragePhotoMainPickActivity :
    BaseActivity<ActivityStoragePhotoMainPickBinding>(R.layout.activity_storage_photo_main_pick) {
    private val photoMainPickRvAdapter = PhotoMainPickRvAdapter(::setNewThumbnail)
    private val photoMainPickViewModel by viewModels<PhotoViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private var thumbnail: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGetPhotoMainPickNetwork()
        setThumbnailUrlOfCard()
        initMainPhotoPickPhotoListObserver()
        setPhotoMainPickViewModelForDataBinding()
        initStorageMainPhotoPickRvAdapter()
        isInitialOpening = true

        initStatusBarColor(R.color.spark_black)
        initMainPhotoPickBackBtnClickListener()
        initMainPhotoPickCompleteBtnClickListener()
        Log.d("씨발"," isInitOpening : ${isInitialOpening}")
    }

    private fun initGetPhotoMainPickNetwork() {
        roomId = intent.getIntExtra("roomId", -1)
        photoMainPickViewModel.initGetPhotoCollectionNetwork(roomId, -1, 70)
    }

    private fun initMainPhotoPickPhotoListObserver() {
        photoMainPickViewModel.photoList.observe(this) { photo ->
            photoMainPickRvAdapter.setPhotoMainList(photo.filter { it.status == "DONE" })
        }
    }

    private fun setThumbnailUrlOfCard() {
        thumbnail = intent.getStringExtra("thumbnail")
        //Log.d("개씨발","${thumbnail}")
        photoMainPickRvAdapter.setPhotoMainThumbnail(requireNotNull(thumbnail))
    }

    private fun setPhotoMainPickViewModelForDataBinding() {
        binding.mainPhotoPickViewModel = photoMainPickViewModel
    }

    private fun initStorageMainPhotoPickRvAdapter() {
        binding.rvStorageMainPhotoPick.adapter = photoMainPickRvAdapter
    }

    private fun initMainPhotoPickBackBtnClickListener() {
        binding.btnCardMainPhotoPickBackWhite.setOnClickListener {
            isInitialOpening = false
            finish()
        }
    }

    private fun initMainPhotoPickCompleteBtnClickListener() {
        binding.tvCardMainPhotoPickComplete.setOnClickListener {
            photoMainPickViewModel.initPatchPhotoMainNetwork()
            val intent = Intent().putExtra("thumbnail",thumbnail)
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    private fun setNewThumbnail(selectedItemPos: Int, patchRecordId: Int) { // 옜날꺼는 불 끄고 누른거 불키고 하려는 함수
        isInitialOpening = false

        val previousThumbnailItemPos = currentThumbnailItemPos
        currentThumbnailItemPos = selectedItemPos
        photoMainPickRvAdapter.notifyItemChanged(previousThumbnailItemPos) //옛날 위치 다시 onBind => currentThumbnail이 position 아니라서 불 꺼짐
        photoMainPickRvAdapter.notifyItemChanged(selectedItemPos) // 선택 위치 다시 onBind => currentTHumbnail이 position 이라서 불 켜짐

        photoMainPickViewModel.setPatchRecordId(patchRecordId)
        photoMainPickViewModel.setPatchRoomId(roomId)
    }
}


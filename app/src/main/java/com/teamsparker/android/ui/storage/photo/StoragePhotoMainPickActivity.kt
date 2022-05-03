package com.teamsparker.android.ui.storage.photo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityStoragePhotoMainPickBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.storage.adapter.PhotoMainPickRvAdapter
import com.teamsparker.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.currentThumbnailItemPos
import com.teamsparker.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.isInitialOpening
import com.teamsparker.android.ui.storage.adapter.PhotoMainPickRvAdapter.Companion.thumbnail
import com.teamsparker.android.ui.storage.viewmodel.PhotoViewModel
import com.teamsparker.android.util.initStatusBarColor
import kotlin.properties.Delegates

class StoragePhotoMainPickActivity :
    BaseActivity<ActivityStoragePhotoMainPickBinding>(R.layout.activity_storage_photo_main_pick) {
    private val photoMainPickRvAdapter = PhotoMainPickRvAdapter(::setNewThumbnail)
    private val photoMainPickViewModel by viewModels<PhotoViewModel>()
    private var roomId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMainPhotoSelectMode()

        initViewModelForDataBinding()
        initRvAdapter()

        getRoomDataFromPhotoCollection()
        initGetPhotoMainPickNetwork()
        setRvAdapterDonePhotoList()

        initStatusBarStyle()
        initMainPhotoPickBackBtnClickListener()
        initMainPhotoPickCompleteBtnClickListener()
    }

    private fun initMainPhotoSelectMode() {
        currentThumbnailItemPos = -1
        isInitialOpening = true
    }

    private fun getRoomDataFromPhotoCollection() {
        roomId = intent.getIntExtra("roomId", -1)
        thumbnail = intent.getStringExtra("thumbnail")!!
    }

    private fun initGetPhotoMainPickNetwork() {
        photoMainPickViewModel.initGetPhotoCollectionNetwork(roomId, -1, 70)
    }

    private fun setRvAdapterDonePhotoList() {
        photoMainPickViewModel.photoList.observe(this) { photo ->
            photoMainPickRvAdapter.setPhotoMainList(photo.filter { it.status == "DONE" })
        }
    }

    private fun initViewModelForDataBinding() {
        binding.mainPhotoPickViewModel = photoMainPickViewModel
    }

    private fun initRvAdapter() {
        binding.rvStorageMainPhotoPick.adapter = photoMainPickRvAdapter
    }

    private fun initMainPhotoPickBackBtnClickListener() {
        binding.btnCardMainPhotoPickBackWhite.setOnClickListener {
            finish()
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_more_deep_gray)
    }

    private fun initMainPhotoPickCompleteBtnClickListener() {
        binding.tvCardMainPhotoPickComplete.setOnClickListener {
            photoMainPickViewModel.initPatchPhotoMainNetwork()
            val intent = Intent(this, StoragePhotoCollectionActivity::class.java)
                .apply {
                    putExtra("new_thumbnail", thumbnail)
                }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setNewThumbnail(
        selectedItemPos: Int,
        patchRecordId: Int
    ) {
        val previousThumbnailItemPos = currentThumbnailItemPos
        currentThumbnailItemPos = selectedItemPos
        photoMainPickRvAdapter.notifyItemChanged(previousThumbnailItemPos)
        photoMainPickRvAdapter.notifyItemChanged(selectedItemPos)

        photoMainPickViewModel.setPatchRecordId(patchRecordId)
        photoMainPickViewModel.setPatchRoomId(roomId)
    }
}

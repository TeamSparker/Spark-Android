package com.spark.android.ui.storage.photo

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityStoragePhotoCollectionBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.main.MainActivity.Companion.IS_FROM_CARD
import com.spark.android.ui.storage.adapter.PhotoCollectionRvAdapter
import com.spark.android.ui.storage.viewmodel.PhotoViewModel
import com.spark.android.util.initStatusBarColor

class StoragePhotoCollectionActivity :
    BaseActivity<ActivityStoragePhotoCollectionBinding>(R.layout.activity_storage_photo_collection) {
    private val photoCollectionRvAdapter = PhotoCollectionRvAdapter()
    private val photoCollectionViewModel by viewModels<PhotoViewModel>()
    private var roomId = 0
    private var thumbnail: String? = ""
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModelForDataBinding()
        initRvAdapter()

        getRoomDataFromStorageCard()
        initPhotoCollectionNetwork()
        setRvAdapterPhotoList()

        initStatusBarStyle()
        initOnBackBtnClickListener()
        initPhotoCollectionMoreBtnClickListener()
    }

    private fun initViewModelForDataBinding() {
        binding.photoCollectionViewModel = photoCollectionViewModel
    }

    private fun initRvAdapter() {
        binding.rvStoragePhotoCollection.adapter = photoCollectionRvAdapter
    }

    private fun getRoomDataFromStorageCard() {
        roomId = intent.getIntExtra("roomId", -1)
        thumbnail = intent.getStringExtra("thumbnail")
    }

    private fun initPhotoCollectionNetwork() {
        photoCollectionViewModel.initGetPhotoCollectionNetwork(roomId, -1, 70)
    }

    private fun setRvAdapterPhotoList() {
        photoCollectionViewModel.photoList.observe(this) { photo ->
            photoCollectionRvAdapter.setList(photo)
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_more_deep_gray)
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.FROM_WHERE, FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY)
            putExtra("cardType",intent.getStringExtra("cardType"))
            IS_FROM_CARD = true
        })
    }

    private fun initOnBackBtnClickListener() {
        binding.btnStoragePhotoCollectionBackWhite.setOnClickListener {
            moveToMain()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveToMain()
    }

    private fun initActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    thumbnail = it.data?.getStringExtra("new_thumbnail")
                }
            }
    }

    private fun initPhotoCollectionMoreBtnClickListener() {
        initActivityResultLauncher()
        binding.btnStoragePhotoCollectionMoreWhite.setOnClickListener {
            PhotoCollectionMoreBottomSheet().apply {
                setChangePhotoBtnClickListener {
                    val intent = Intent(context, StoragePhotoMainPickActivity::class.java).apply {
                        putExtra("roomId", roomId)
                        putExtra("thumbnail", thumbnail)
                    }
                    activityResultLauncher.launch(intent)
                }
            }.show(supportFragmentManager, this.javaClass.name)
        }
    }

    companion object {
        const val FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY = "StoragePhotoCollectionActivity"
    }
}

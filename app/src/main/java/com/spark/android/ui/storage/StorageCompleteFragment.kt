package com.spark.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageCompleteBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.CompleteVpAdapter
import com.spark.android.ui.storage.viewmodel.StorageViewModel

class StorageCompleteFragment :
    BaseFragment<FragmentStorageCompleteBinding>(R.layout.fragment_storage_complete) {
    private val completeVpAdapter = CompleteVpAdapter()
    private val storageViewModel by activityViewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCompleteVpAdapter()
        initCompleteRoomsObserver()
    }

    private fun initCompleteVpAdapter() {
        binding.vpStorageComplete.adapter = completeVpAdapter
        binding.vpStorageComplete.offscreenPageLimit = 3
        binding.vpStorageComplete.post {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val pagerWidth = binding.vpStorageComplete.width
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth
            binding.vpStorageComplete.setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }

    private fun initCompleteRoomsObserver() {
        storageViewModel.completeRooms.observe(viewLifecycleOwner) { rooms ->
            completeVpAdapter.setList(rooms)
        }
    }
}
package com.spark.android.ui.storage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageProgressingBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.ProgressingVpAdapter
import com.spark.android.ui.storage.viewmodel.StorageViewModel

class StorageProgressingFragment :
    BaseFragment<FragmentStorageProgressingBinding>(R.layout.fragment_storage_progressing) {
    private val progressingVpAdapter = ProgressingVpAdapter()
    private val storageViewModel by activityViewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
        initProgressingVpAdapter()
        initProgressingRoomsObserver()
    }

    private fun initProgressingVpAdapter() {
        binding.vpStorageProgressing.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpStorageProgressing.adapter = progressingVpAdapter
        binding.vpStorageProgressing.offscreenPageLimit = 3
        binding.vpStorageProgressing.post {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val pagerWidth = binding.vpStorageProgressing.width
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth
            binding.vpStorageProgressing.setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }

    private fun initProgressingRoomsObserver() {
        storageViewModel.progressingRooms.observe(viewLifecycleOwner) { rooms ->
            progressingVpAdapter.setList(rooms)
        }
    }
}
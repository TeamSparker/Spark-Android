package com.spark.android.ui.storage

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
    private lateinit var progressingVpAdapter: ProgressingVpAdapter
    private val storageViewModel by activityViewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
    }

    override fun onResume() {
        super.onResume()

        progressingVpAdapter = ProgressingVpAdapter()
        initProgressingVpAdapter()
        initProgressingRoomsObserver()
        progressingVpAdapter.notifyDataSetChanged()

        storageViewModel.currentProgressingMode.observe(viewLifecycleOwner) { currentMode ->
            if (currentMode) {
                binding.vpStorageProgressing.currentItem = 0
                progressingVpAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initProgressingVpAdapter() {
        binding.vpStorageProgressing.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpStorageProgressing.adapter = progressingVpAdapter
        binding.vpStorageProgressing.offscreenPageLimit = 1
        binding.vpStorageProgressing.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun initProgressingRoomsObserver() {
        storageViewModel.progressingRooms.observe(viewLifecycleOwner) { rooms ->
            progressingVpAdapter.updateProgressingRoomList(rooms)
        }
    }
}

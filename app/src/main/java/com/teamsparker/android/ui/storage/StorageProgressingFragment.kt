package com.teamsparker.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentStorageProgressingBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.storage.adapter.ProgressingVpAdapter
import com.teamsparker.android.ui.storage.viewmodel.StorageViewModel

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

package com.teamsparker.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentStorageCompleteBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.storage.adapter.CompleteVpAdapter
import com.teamsparker.android.ui.storage.viewmodel.StorageViewModel

class StorageCompleteFragment :
    BaseFragment<FragmentStorageCompleteBinding>(R.layout.fragment_storage_complete) {
    private lateinit var  completeVpAdapter : CompleteVpAdapter
    private val storageViewModel by activityViewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
    }

    override fun onResume() {
        super.onResume()
        completeVpAdapter = CompleteVpAdapter()
        initCompleteVpAdapter()
        initCompleteRoomsObserver()
        completeVpAdapter.notifyDataSetChanged()

        storageViewModel.currentCompleteMode.observe(viewLifecycleOwner){ currentMode ->
            if (currentMode) {
                binding.vpStorageComplete.currentItem = 0
                completeVpAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initCompleteVpAdapter() {
        binding.vpStorageComplete.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpStorageComplete.adapter = completeVpAdapter
        binding.vpStorageComplete.offscreenPageLimit = 1
        binding.vpStorageComplete.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun initCompleteRoomsObserver() {
        storageViewModel.completeRooms.observe(viewLifecycleOwner) { rooms ->
            completeVpAdapter.updateCompleteRoomList(rooms)
        }
    }
}

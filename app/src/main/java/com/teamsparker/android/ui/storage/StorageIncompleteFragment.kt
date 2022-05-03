package com.teamsparker.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentStorageIncompleteBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.storage.adapter.IncompleteVpAdapter
import com.teamsparker.android.ui.storage.viewmodel.StorageViewModel

class StorageIncompleteFragment :
    BaseFragment<FragmentStorageIncompleteBinding>(R.layout.fragment_storage_incomplete) {
    private lateinit var incompleteVpAdapter: IncompleteVpAdapter
    private val storageViewModel by activityViewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
    }

    override fun onResume() {
        super.onResume()
        incompleteVpAdapter = IncompleteVpAdapter()
        initIncompleteVpAdapter()
        initIncompleteRoomsObserver()
        incompleteVpAdapter.notifyDataSetChanged()

        storageViewModel.currentIncompleteMode.observe(viewLifecycleOwner) { currentMode ->
            if (currentMode) {
                binding.vpStorageIncomplete.currentItem = 0
                incompleteVpAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initIncompleteVpAdapter() {
        binding.vpStorageIncomplete.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpStorageIncomplete.adapter = incompleteVpAdapter
        binding.vpStorageIncomplete.offscreenPageLimit = 1
        binding.vpStorageIncomplete.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun initIncompleteRoomsObserver() {
        storageViewModel.incompleteRooms.observe(viewLifecycleOwner) { rooms ->
            incompleteVpAdapter.updateIncompleteRoomList(rooms)
        }
    }
}

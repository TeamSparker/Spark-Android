package com.spark.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageIncompleteBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.IncompleteVpAdapter
import com.spark.android.ui.storage.viewmodel.StorageViewModel

class StorageIncompleteFragment :
    BaseFragment<FragmentStorageIncompleteBinding>(R.layout.fragment_storage_incomplete) {
    private val incompleteVpAdapter = IncompleteVpAdapter()
    private val storageViewModel by activityViewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
        initIncompleteVpAdapter()
        initIncompleteRoomsObserver()
    }

    private fun initIncompleteVpAdapter() {
        binding.vpStorageIncomplete.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpStorageIncomplete.adapter = incompleteVpAdapter
        binding.vpStorageIncomplete.offscreenPageLimit = 3
        binding.vpStorageIncomplete.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun initIncompleteRoomsObserver() {
        storageViewModel.incompleteRooms.observe(viewLifecycleOwner) { rooms ->
            incompleteVpAdapter.setList(rooms)
        }
    }
}

package com.spark.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.StorageViewPagerOutAdapter
import com.spark.android.ui.storage.viewmodel.StorageViewModel


class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {
    private lateinit var viewPagerOutAdapter: StorageViewPagerOutAdapter
    private val storageViewModel: StorageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
        initStorageOutAdapter()
        initModeObserver()
        binding.vpStorageOut.isUserInputEnabled = false;
    }

    private fun initStorageOutAdapter() {
        val fragmentList = listOf(
            StorageProgressingFragment(),
            StorageCompleteFragment(),
            StorageIncompleteFragment()
        )
        viewPagerOutAdapter = StorageViewPagerOutAdapter(requireActivity())
        viewPagerOutAdapter.fragments.addAll(fragmentList)
        binding.vpStorageOut.adapter = viewPagerOutAdapter
    }

    private fun initModeObserver() {
        storageViewModel.sparkMode.observe(viewLifecycleOwner) { mode ->
            binding.vpStorageOut.currentItem = mode
        }
    }
}
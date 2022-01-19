package com.spark.android.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.StorageMode.Companion.COMPLETE
import com.spark.android.ui.storage.StorageMode.Companion.INCOMPLETE
import com.spark.android.ui.storage.StorageMode.Companion.PROGRESSING
import com.spark.android.ui.storage.adapter.StorageViewPagerOutAdapter
import com.spark.android.ui.storage.viewmodel.StorageViewModel
import java.lang.IllegalStateException


class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {
    private lateinit var viewPagerOutAdapter: StorageViewPagerOutAdapter
    private val storageViewModel by activityViewModels<StorageViewModel>()

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
        storageViewModel.storageMode.observe(viewLifecycleOwner) { mode ->
            when (mode) {
                PROGRESSING -> {
                    binding.vpStorageOut.currentItem = 0
                    storageViewModel.initStorageNetwork(PROGRESSING, -1, 5)
                }
                COMPLETE -> {
                    binding.vpStorageOut.currentItem = 1
                    storageViewModel.initStorageNetwork(COMPLETE, -1, 5)
                }
                INCOMPLETE -> {
                    binding.vpStorageOut.currentItem = 2
                    storageViewModel.initStorageNetwork(INCOMPLETE, -1, 5)
                }
                else -> throw IllegalStateException()
            }
        }
    }

}
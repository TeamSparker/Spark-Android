package com.spark.android.ui.storage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.viewmodel.StorageViewModel
import java.util.Observer


class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {
    private lateinit var viewPagerOutAdapter: StorageViewPagerOutAdapter
    private val storageViewModel: StorageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
        initStorageOutAdapter()

        storageViewModel.sparkMode.observe(viewLifecycleOwner) { mode ->
            binding.vpStorageOut.currentItem = mode
        }

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

}
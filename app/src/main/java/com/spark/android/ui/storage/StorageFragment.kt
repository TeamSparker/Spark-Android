package com.spark.android.ui.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageBinding
import com.spark.android.ui.base.BaseFragment


class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {
    private lateinit var viewPagerOutAdapter: StorageViewPagerOutAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStorageOutAdapter()

    }

    private fun initStorageOutAdapter() {
        val fragmentList = listOf(StorageProgressingFragment(),StorageCompleteFragment(),StorageIncompleteFragment())
        viewPagerOutAdapter = StorageViewPagerOutAdapter(requireActivity())
        viewPagerOutAdapter.fragments.addAll(fragmentList)

        binding.vpStorageOut.adapter = viewPagerOutAdapter
    }

}
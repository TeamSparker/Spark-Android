package com.spark.android.ui.storage

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageIncompleteBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.IncompleteVpAdapter


class StorageIncompleteFragment :
    BaseFragment<FragmentStorageIncompleteBinding>(R.layout.fragment_storage_incomplete) {
    private val incompleteVpAdapter = IncompleteVpAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIncompleteVpAdapter()
    }

    private fun initIncompleteVpAdapter() {
        incompleteVpAdapter.setList(listOf("미완료 1", "미완료 2", "미완료 3", "미완료 4"))
        binding.vpStorageIncomplete.adapter = incompleteVpAdapter
        binding.vpStorageIncomplete.offscreenPageLimit = 3
        binding.vpStorageIncomplete.post {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val pagerWidth = binding.vpStorageIncomplete.width
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth
            binding.vpStorageIncomplete.setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }
}
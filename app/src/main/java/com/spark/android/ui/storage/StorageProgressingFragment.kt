package com.spark.android.ui.storage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageProgressingBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.ProgressingVpAdapter

class StorageProgressingFragment :
    BaseFragment<FragmentStorageProgressingBinding>(R.layout.fragment_storage_progressing) {
    private val progressingVpAdapter = ProgressingVpAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProgressingVpAdapter()
    }

    private fun initProgressingVpAdapter() {
        progressingVpAdapter.setList(listOf("진행1", "진행2", "진행3", "진행4"))
        binding.vpStorageProgressing.adapter = progressingVpAdapter
        binding.vpStorageProgressing.offscreenPageLimit = 3
        binding.vpStorageProgressing.post {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val pagerWidth = binding.vpStorageProgressing.width
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth
            binding.vpStorageProgressing.setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }
}
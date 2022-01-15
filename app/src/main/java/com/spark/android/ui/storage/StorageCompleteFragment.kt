package com.spark.android.ui.storage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.spark.android.R
import com.spark.android.databinding.FragmentStorageCompleteBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.storage.adapter.CompleteVpAdapter


class StorageCompleteFragment :
    BaseFragment<FragmentStorageCompleteBinding>(R.layout.fragment_storage_complete) {
    private val completeVpAdapter = CompleteVpAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCompleteVpAdapter()
    }

    private fun initCompleteVpAdapter() {
        completeVpAdapter.setList(listOf("완료1", "완료2", "완료3", "완료4"))
        binding.vpStorageComplete.adapter = completeVpAdapter
        binding.vpStorageComplete.offscreenPageLimit = 3
        binding.vpStorageComplete.post {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val pagerWidth = binding.vpStorageComplete.width
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth
            binding.vpStorageComplete.setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }
}
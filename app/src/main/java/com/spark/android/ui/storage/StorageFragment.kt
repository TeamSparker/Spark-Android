package com.spark.android.ui.storage

import android.animation.AnimatorInflater
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
    private val progressingIndicatorAnim by lazy { getIndicatorAnimator(binding.viewStorageProgressingIndicator) }
    private val incompleteIndicatorAnim by lazy { getIndicatorAnimator(binding.viewStorageIncompleteIndicator) }
    private val completeIndicatorAnim by lazy { getIndicatorAnimator(binding.viewStorageCompleteIndicator) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storageViewModel = storageViewModel
        storageViewModel.initProgressMode()
        initStorageOutAdapter()
        initModeObserver()
        binding.vpStorageOut.isUserInputEnabled = false;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        storageViewModel.initFirstLoading(false)
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
                    progressingIndicatorAnim.start()
                    storageViewModel.initStorageNetwork(PROGRESSING, -1, 30)
                }
                COMPLETE -> {
                    binding.vpStorageOut.currentItem = 1
                    completeIndicatorAnim.start()
                    storageViewModel.initStorageNetwork(COMPLETE, -1, 30)
                }
                INCOMPLETE -> {
                    binding.vpStorageOut.currentItem = 2
                    incompleteIndicatorAnim.start()
                    storageViewModel.initStorageNetwork(INCOMPLETE, -1, 30)
                }
                else -> throw IllegalStateException()
            }
        }
    }

    private fun getIndicatorAnimator(view: View) = AnimatorInflater
        .loadAnimator(
            context,
            R.animator.animator_storage_indicator
        ).apply {
            setTarget(view)
        }

}
